package com.example.mindfulmate.presentation.ui.screen.community

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.model.community.Comment
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.community.component.community_post.CommunityPostCommentField
import com.example.mindfulmate.presentation.ui.screen.community.component.community_post.CommunityPostHeader
import com.example.mindfulmate.presentation.ui.screen.community.component.community_post.CommunityPostQuestion
import com.example.mindfulmate.presentation.ui.screen.community.component.community_post.MultipleCommunityPostComments
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunityAnswerParams
import com.example.mindfulmate.presentation.view_model.community.community_post.CommunityPostUiState
import com.example.mindfulmate.presentation.view_model.community.community_post.CommunityPostViewModel

@Composable
fun CommunityPostScreen(
    viewModel: CommunityPostViewModel,
    communityId: String,
    postId: String,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: CommunityPostUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val comments: List<Comment> by viewModel.comments.collectAsStateWithLifecycle()
    val likedPosts by viewModel.likedPostsState.collectAsStateWithLifecycle()
    val likeCounts by viewModel.postLikeCounts.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchPostAndComments(communityId, postId)
        viewModel.checkIfPostIsLiked(postId)
    }

    when (uiState) {
        is CommunityPostUiState.Loading -> {
            LoadingPlaceholder()
        }
        is CommunityPostUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = onGoBackClick)
        }
        is CommunityPostUiState.Success -> {
            val post = (uiState as CommunityPostUiState.Success).post
            CommunityPostScreen(
                username = post.username,
                date = viewModel.formatDate(post.date),
                questionTitle = post.title,
                questionDescription = post.body,
                likeCount = likeCounts[post.postId]?.toString() ?: post.likes.toString(),
                commentCount = post.commentsCount.toString(),
                comments = comments.map { comment ->
                    CommunityAnswerParams(
                        username = comment.username,
                        comment = comment.comment
                    )
                },
                isSaved = likedPosts[post.postId] ?: false,
                onMessageSend = { message ->
                    viewModel.sendComment(
                        communityId = communityId,
                        postId = postId,
                        commentText = message
                    )
                },
                onLikeClick = { viewModel.toggleLikePost(communityId, postId) },
                onGoBackClick = onGoBackClick,
                modifier = modifier
            )
        }
        CommunityPostUiState.Init -> {}
    }
}


@Composable
private fun CommunityPostScreen(
    username: String,
    date: String,
    questionTitle: String,
    questionDescription: String,
    likeCount: String,
    commentCount: String,
    isSaved: Boolean,
    comments: List<CommunityAnswerParams>,
    onMessageSend: (String) -> Unit,
    onLikeClick: (Boolean) -> Unit,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            CommunityPostHeader(
                onGoBackClick = onGoBackClick,
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                    horizontal = dimensionResource(id = R.dimen.padding_default)
                )
            )
            CommunityPostQuestion(
                username = username,
                date = date,
                questionTitle = questionTitle,
                questionDescription = questionDescription,
                likeCount = likeCount,
                commentCount = commentCount,
                onLikeClick = onLikeClick,
                isSaved = isSaved
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
            MultipleCommunityPostComments(communityComments = comments)
        }
        CommunityPostCommentField(
            onMessageSend = onMessageSend,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
private fun CommunityPostScreenPreview() {
    MindfulMateTheme {
        CommunityPostScreen(
            username = "String",
            date = "String",
            questionTitle = "String",
            questionDescription = "String",
            likeCount = "String",
            commentCount = "String",
            comments = listOf(
                CommunityAnswerParams(
                    username = "comment.username",
                    comment = "comment.comment"
                )
            ),
            onMessageSend = {},
            onLikeClick = {},
            onGoBackClick = {},
            isSaved = false
        )
    }
}


@Preview
@Composable
private fun NoCommentsCommunityPostScreenPreview() {
    MindfulMateTheme {
        CommunityPostScreen(
            username = "String",
            date = "String",
            questionTitle = "String",
            questionDescription = "String",
            likeCount = "String",
            commentCount = "String",
            comments = emptyList(),
            onMessageSend = {},
            onLikeClick = {},
            onGoBackClick = {},
            isSaved = true
        )
    }
}
