package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class IsCommunitySavedByUserUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String): Boolean {
        return communityRepository.isCommunitySavedByUser(communityId)
    }
}
