package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.model.community.Post
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String, postId: String): Post {
        return communityRepository.getPost(communityId, postId)
    }
}
