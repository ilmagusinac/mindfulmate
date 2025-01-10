package com.example.mindfulmate.domain.usecase.community

import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.domain.repository.community.CommunityRepository
import javax.inject.Inject

class WriteCommentUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    suspend operator fun invoke(communityId: String, postId: String, commentText: String) {
        communityRepository.writeComment(communityId, postId, commentText)
    }
}
