package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(userId: String): String?  {
        return communityRepository.fetchUsername(userId)
    }
}
