package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class EditCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String, postId: String, commentId: String, newCommentText: String) {
        communityRepository.editComment(communityId, postId, commentId, newCommentText)
    }
}
