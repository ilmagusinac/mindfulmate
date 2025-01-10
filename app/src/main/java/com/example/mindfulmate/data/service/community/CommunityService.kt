package com.example.mindfulmate.data.service.community

import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.model.community.Community
import com.example.mindfulmate.domain.model.community.Post

interface CommunityService {
    suspend fun getAllCommunities(): List<Community>
    suspend fun getUserCommunities(): List<Community>
    suspend fun getCommunityDetails(communityId: String): Community
    suspend fun getCommunityPosts(communityId: String): List<Post>
    suspend fun writePost(communityId: String, post: Post)
    suspend fun getPost(communityId: String, postId: String): Post
    suspend fun getComments(communityId: String, postId: String): List<Comment>
    suspend fun writeComment(communityId: String, postId: String, commentText: String)
    suspend fun addUserToCommunity(communityId: String)
    suspend fun removeUserFromCommunity(communityId: String)
    suspend fun isCommunitySavedByUser(communityId: String): Boolean
    suspend fun likePost(communityId: String, postId: String)
    suspend fun unlikePost(communityId: String, postId: String)
    suspend fun isPostLikedByUser(postId: String): Boolean
}
