package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class IsPostLikedUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(postId: String): Boolean {
        return communityRepository.isPostLikedByUser(postId)
    }
}
