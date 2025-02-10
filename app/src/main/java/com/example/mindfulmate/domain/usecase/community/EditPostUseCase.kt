package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class EditPostUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String, postId: String, newTitle: String, newBody: String)  {
        return communityRepository.editPost(communityId, postId, newTitle, newBody)
    }
}
