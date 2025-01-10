package com.example.mindfulmate.data.repository.community

import com.example.mindfulmate.data.service.community.CommunityService
import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.model.community.Community
import com.example.mindfulmate.domain.model.community.Post
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityService: CommunityService
) : CommunityRepository {

    override suspend fun getAllCommunities(): List<Community> =
        communityService.getAllCommunities()

    override suspend fun getUserCommunities(): List<Community> =
        communityService.getUserCommunities()

    override suspend fun getCommunityDetails(communityId: String): Community {
        return communityService.getCommunityDetails(communityId)
    }

    override suspend fun getCommunityPosts(communityId: String): List<Post> {
        return communityService.getCommunityPosts(communityId)
    }

    override suspend fun writePost(communityId: String, post: Post) =
        communityService.writePost(communityId, post)

    override suspend fun getPost(communityId: String, postId: String): Post {
        return communityService.getPost(communityId, postId)
    }

    override suspend fun getComments(communityId: String, postId: String): List<Comment> {
        return communityService.getComments(communityId, postId)
    }

    override suspend fun writeComment(communityId: String, postId: String, commentText: String) =
        communityService.writeComment(communityId, postId, commentText)

    override suspend fun addUserToCommunity(communityId: String) =
        communityService.addUserToCommunity(communityId)

    override suspend fun removeUserFromCommunity(communityId: String) =
        communityService.removeUserFromCommunity(communityId)

    override suspend fun isCommunitySavedByUser(communityId: String): Boolean {
        return communityService.isCommunitySavedByUser(communityId)
    }

    override suspend fun likePost(communityId: String, postId: String) =
        communityService.likePost(communityId, postId)

    override suspend fun unlikePost(communityId: String, postId: String) =
        communityService.unlikePost(communityId, postId)

    override suspend fun isPostLikedByUser(postId: String): Boolean {
        return communityService.isPostLikedByUser(postId)
    }
}
