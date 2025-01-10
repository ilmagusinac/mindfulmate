package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.model.community.Community
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class GetUserCommunities @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(): List<Community> {
        return communityRepository.getUserCommunities()
    }
}
