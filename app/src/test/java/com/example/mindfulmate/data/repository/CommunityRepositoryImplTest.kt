package com.example.mindfulmate.data.repository

import com.example.mindfulmate.data.repository.community.CommunityRepositoryImpl
import com.example.mindfulmate.data.service.community.CommunityService
import com.example.mindfulmate.domain.model.community.Community
import com.example.mindfulmate.domain.model.community.Post
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CommunityRepositoryImplTest {

    private lateinit var communityService: CommunityService
    private lateinit var communityRepository: CommunityRepositoryImpl

    @Before
    fun setUp() {
        communityService = mockk()
        communityRepository = CommunityRepositoryImpl(communityService)
    }

    @Test
    fun `getAllCommunities calls service method and returns result`() = runBlocking {
        val communities = listOf(
            Community(id = "1", communityName = "Community Name", membersCount = 10),
            Community(id = "2", communityName = "Another Community")
        )
        coEvery { communityService.getAllCommunities() } returns communities

        val result = communityRepository.getAllCommunities()

        coVerify { communityService.getAllCommunities() }
        assertEquals(communities, result)
    }


    @Test
    fun `getUserCommunities calls service method and returns result`() = runBlocking {
        val communities = listOf(Community(id = "2", communityName = "Community 2", membersCount = 100))
        coEvery { communityService.getUserCommunities() } returns communities

        val result = communityRepository.getUserCommunities()

        coVerify { communityService.getUserCommunities() }
        assertEquals(communities, result)
    }

    @Test
    fun `getCommunityDetails calls service method and returns result`() = runBlocking {
        val communityId = "123"
        val community = Community(id = communityId, communityName = "Community Details", membersCount = 200)
        coEvery { communityService.getCommunityDetails(communityId) } returns community

        val result = communityRepository.getCommunityDetails(communityId)

        coVerify { communityService.getCommunityDetails(communityId) }
        assertEquals(community, result)
    }

    @Test
    fun `getCommunityPosts calls service method and returns result`() = runBlocking {
        val communityId = "123"
        val posts = listOf(
            Post(postId = "1", title = "Post Title", body = "Post Body", likes = 50, commentsCount = 5)
        )
        coEvery { communityService.getCommunityPosts(communityId) } returns posts

        val result = communityRepository.getCommunityPosts(communityId)

        coVerify { communityService.getCommunityPosts(communityId) }
        assertEquals(posts, result)
    }

    @Test
    fun `writePost calls service method`() = runBlocking {
        val communityId = "123"
        val post = Post(postId = "1", title = "Post Title", body = "Post Body")
        coEvery { communityService.writePost(communityId, post) } just Runs

        communityRepository.writePost(communityId, post)

        coVerify { communityService.writePost(communityId, post) }
    }

    @Test
    fun `isCommunitySavedByUser calls service method and returns result`() = runBlocking {
        val communityId = "123"
        coEvery { communityService.isCommunitySavedByUser(communityId) } returns true

        val result = communityRepository.isCommunitySavedByUser(communityId)

        coVerify { communityService.isCommunitySavedByUser(communityId) }
        assertEquals(true, result)
    }

    @Test
    fun `likePost calls service method`() = runBlocking {
        val communityId = "123"
        val postId = "456"
        coEvery { communityService.likePost(communityId, postId) } just Runs

        communityRepository.likePost(communityId, postId)

        coVerify { communityService.likePost(communityId, postId) }
    }

    @Test
    fun `getCurrentUserId calls service method and returns result`() = runBlocking {
        val currentUserId = "user123"

        coEvery { communityService.getCurrentUserId() } returns currentUserId
        val result = communityRepository.getCurrentUserId()

        coVerify { communityService.getCurrentUserId() }
        assertEquals(currentUserId, result)
    }
}
