package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.model.community.Post
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class WritePostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String, post: Post) {
        communityRepository.writePost(communityId, post)
    }
}
