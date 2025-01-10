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
): CommunityService {

    override suspend fun getAllCommunities(): List<Community> {
        try {
            val snapshot = firestore.collection("communities")
                .get()
                .await()

            return snapshot.documents.mapNotNull { document ->
                val community = document.toObject(Community::class.java)?.copy(id = document.id)
                community
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun addUserToCommunity(communityId: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        try {
            firestore.collection("users")
                .document(userId)
                .update("myCommunities", FieldValue.arrayUnion(communityId))
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to save community: ${e.message}")
        }
    }

    override suspend fun removeUserFromCommunity(communityId: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        try {
            firestore.collection("users")
                .document(userId)
                .update("myCommunities", FieldValue.arrayRemove(communityId))
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to unsave community: ${e.message}")
        }
    }

    override suspend fun getUserCommunities(): List<Community> {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val userDoc = firestore.collection("users").document(userId).get().await()
        val communityIds = userDoc.get("myCommunities") as? List<String> ?: emptyList()
        println("communityids: $communityIds")
        val communities = communityIds.mapNotNull { communityId ->
            val communityDoc = firestore.collection("communities").document(communityId).get().await()
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

        val postWithId = post.copy(postId = postId)

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
        val userId = getCurrentUserId()
        val username = fetchUsername(userId) ?: "Anonymous"

        try {
            // 1. Add the comment without setting the commentId first
            val commentRef = firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .collection("comments")
                .add(
                    Comment(
                        userId = userId,
                        commentId = "",  // Temporarily empty, will be updated below
                        comment = commentText,
                        username = username,
                        timestamp = com.google.firebase.Timestamp.now()
                    )
                ).await()

            // 2. Update the commentId field with the auto-generated document ID
            commentRef.update("commentId", commentRef.id).await()

            // 3. Increment the commentsCount in the post document
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

    private suspend fun fetchUsername(userId: String): String? {
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

    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: throw Exception("User not logged in")
    }

    override suspend fun likePost(communityId: String, postId: String) {
        val userId = getCurrentUserId()
        try {
            // Increment the likes in the post
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update("likes", FieldValue.increment(1))
                .await()

            // Add postId to the user's likedPosts
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
        val userId = getCurrentUserId()
        try {
            // Decrement the likes in the post
            firestore.collection("communities")
                .document(communityId)
                .collection("posts")
                .document(postId)
                .update("likes", FieldValue.increment(-1))
                .await()

            // Remove postId from the user's likedPosts
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
            val userId = getCurrentUserId()
            val userSnapshot = firestore.collection("users").document(userId).get().await()
            val likedPosts = userSnapshot.get("likedPosts") as? List<String> ?: emptyList()
            likedPosts.contains(postId)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
