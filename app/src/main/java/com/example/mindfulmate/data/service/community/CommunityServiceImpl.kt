package com.example.mindfulmate.data.service.community

import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.model.community.Community
import com.example.mindfulmate.domain.model.community.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommunityServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : CommunityService {

    override suspend fun getAllCommunities(): List<Community> {
        try {
            val snapshot = firestore.collection("communities")
                .get()
                .await()

            return snapshot.documents.mapNotNull { document ->
                document.toObject(Community::class.java)?.copy(
                    id = document.id,
                    profilePicture = document.getString("profilePicture") ?: "",
                    backgroundPicture = document.getString("backgroundPicture") ?: ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun addUserToCommunity(communityId: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        try {
            val communityRef = firestore.collection("communities").document(communityId)
            val userRef = firestore.collection("users").document(userId)

            val communitySnapshot = communityRef.get().await()
            val currentCount = communitySnapshot.getLong("membersCount") ?: 0

            firestore.runTransaction { transaction ->
                transaction.update(userRef, "myCommunities", FieldValue.arrayUnion(communityId))

                transaction.update(communityRef, "membersCount", currentCount + 1)
            }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to add user to community: ${e.message}")
        }
    }

    override suspend fun removeUserFromCommunity(communityId: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        try {
            val communityRef = firestore.collection("communities").document(communityId)
            val userRef = firestore.collection("users").document(userId)

            val communitySnapshot = communityRef.get().await()
            val currentCount = communitySnapshot.getLong("membersCount") ?: 1

            firestore.runTransaction { transaction ->
                transaction.update(userRef, "myCommunities", FieldValue.arrayRemove(communityId))

                transaction.update(communityRef, "membersCount", maxOf(0, currentCount - 1))
            }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to remove user from community: ${e.message}")
        }
    }

    override suspend fun getUserCommunities(): List<Community> {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val userDoc = firestore.collection("users").document(userId).get().await()
        val communityIds = userDoc.get("myCommunities") as? List<String> ?: emptyList()
        val communities = communityIds.mapNotNull { communityId ->
            val communityDoc =
                firestore.collection("communities").document(communityId).get().await()
            communityDoc.toObject(Community::class.java)?.copy(id = communityId)
        }
        return communities
    }

    override suspend fun getCommunityDetails(communityId: String): Community {
        val snapshot = firestore.collection("communities").document(communityId).get().await()
        return snapshot.toObject(Community::class.java)?.copy(id = communityId)
            ?: throw Exception("Community not found")
    }

    override suspend fun getCommunityPosts(communityId: String): List<Post> {
        val snapshot = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
    }

    override suspend fun writePost(communityId: String, post: Post) {
        val postId = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .document()
            .id

        val userId = getCurrentUsersId()

        val postWithId = post.copy(postId = postId, userId = userId)

        try {
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .set(postWithId)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to create post: ${e.message}")
        }
    }


    override suspend fun getPost(communityId: String, postId: String): Post {
        val snapshot = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .document(postId)
            .get()
            .await()
        return snapshot.toObject(Post::class.java) ?: throw Exception("Post not found")
    }

    override suspend fun getComments(communityId: String, postId: String): List<Comment> {
        val snapshot = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .document(postId)
            .collection("comments")
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(Comment::class.java) }
    }

    override suspend fun writeComment(communityId: String, postId: String, commentText: String) {
        val userId = getCurrentUsersId()
        val username = fetchUsername(userId) ?: "Anonymous"

        try {
            val commentRef = firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .collection("comments")
                .add(
                    Comment(
                        userId = userId,
                        commentId = "",
                        comment = commentText,
                        username = username,
                        timestamp = com.google.firebase.Timestamp.now()
                    )
                ).await()
            commentRef.update("commentId", commentRef.id).await()
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update("commentsCount", FieldValue.increment(1))
                .await()

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to write comment: ${e.message}")
        }
    }

    override suspend fun fetchUsername(userId: String): String? {
        return try {
            val userSnapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()
            userSnapshot.getString("username")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    private fun getCurrentUsersId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    override suspend fun isCommunitySavedByUser(communityId: String): Boolean {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")

            val userSnapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            val savedCommunities = userSnapshot.get("myCommunities") as? List<String> ?: emptyList()

            savedCommunities.contains(communityId)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun likePost(communityId: String, postId: String) {
        val userId = getCurrentUsersId()
        try {
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update("likes", FieldValue.increment(1))
                .await()

            firestore.collection("users")
                .document(userId)
                .update("likedPosts", FieldValue.arrayUnion(postId))
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to like post: ${e.message}")
        }
    }

    override suspend fun unlikePost(communityId: String, postId: String) {
        val userId = getCurrentUsersId()
        try {
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update("likes", FieldValue.increment(-1))
                .await()

            firestore.collection("users")
                .document(userId)
                .update("likedPosts", FieldValue.arrayRemove(postId))
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to unlike post: ${e.message}")
        }
    }

    override suspend fun isPostLikedByUser(postId: String): Boolean {
        return try {
            val userId = getCurrentUsersId()
            val userSnapshot = firestore.collection("users").document(userId).get().await()
            val likedPosts = userSnapshot.get("likedPosts") as? List<String> ?: emptyList()
            likedPosts.contains(postId)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun deletePost(communityId: String, postId: String) {
        val userId = getCurrentUsersId()

        try {
            val postSnapshot = firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .get()
                .await()

            val postOwnerId = postSnapshot.getString("userId")

            if (postOwnerId == userId) {

                val commentsSnapshot = firestore.collection("communities")
                    .document(communityId)
                    .collection("posts")
                    .document(postId)
                    .collection("comments")
                    .get()
                    .await()

                commentsSnapshot.documents.forEach { comment ->
                    comment.reference.delete().await()
                }

                firestore.collection("communities")
                    .document(communityId)
                    .collection("posts")
                    .document(postId)
                    .delete()
                    .await()
            } else {

                throw Exception("You are not authorized to delete this post")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to delete post: ${e.message}")
        }
    }

    override suspend fun editPost(
        communityId: String,
        postId: String,
        newTitle: String,
        newBody: String
    ) {
        val userId = getCurrentUsersId()
        val postSnapshot = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .document(postId)
            .get()
            .await()

        val postOwnerId = postSnapshot.getString("userId")

        if (postOwnerId == userId) {
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update(
                    mapOf(
                        "title" to newTitle,
                        "body" to newBody
                    )
                ).await()
        } else {
            throw Exception("You are not authorized to edit this post")
        }
    }

    override suspend fun deleteComment(communityId: String, postId: String, commentId: String) {
        val userId = getCurrentUsersId()
        val commentSnapshot = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .document(postId)
            .collection("comments")
            .document(commentId)
            .get()
            .await()

        val commentOwnerId = commentSnapshot.getString("userId")

        if (commentOwnerId == userId) {
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .collection("comments")
                .document(commentId)
                .delete()
                .await()

            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update("commentsCount", FieldValue.increment(-1))
                .await()
        } else {
            throw Exception("You are not authorized to delete this comment")
        }
    }

    override suspend fun editComment(
        communityId: String,
        postId: String,
        commentId: String,
        newCommentText: String
    ) {
        val userId = getCurrentUsersId()
        val commentSnapshot = firestore.collection("communities")
            .document(communityId)
            .collection("posts")
            .document(postId)
            .collection("comments")
            .document(commentId)
            .get()
            .await()

        val commentOwnerId = commentSnapshot.getString("userId")

        if (commentOwnerId == userId) {
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .collection("comments")
                .document(commentId)
                .update("comment", newCommentText)
                .await()
        } else {
            throw Exception("You are not authorized to edit this comment")
        }
    }
}
