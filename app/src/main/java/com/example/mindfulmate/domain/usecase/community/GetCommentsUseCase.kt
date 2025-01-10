package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String, postId: String): List<Comment> {
        return communityRepository.getComments(communityId, postId)
    }
}
