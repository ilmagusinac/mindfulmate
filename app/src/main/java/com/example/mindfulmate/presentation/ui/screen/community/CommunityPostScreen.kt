package com.example.mindfulmate.presentation.ui.screen.community

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
    onChatClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: CommunityPostUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val comments: List<Comment> by viewModel.comments.collectAsStateWithLifecycle()
    val likedPosts by viewModel.likedPostsState.collectAsStateWithLifecycle()
    val likeCounts by viewModel.postLikeCounts.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val postUserProfileImage by viewModel.postUserProfileImage.collectAsStateWithLifecycle()
    val commentUserProfiles by viewModel.commentUserProfiles.collectAsStateWithLifecycle()
    val selectedCommentId by viewModel.selectedCommentId.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val editingCommentText by viewModel.editingCommentText.collectAsStateWithLifecycle()
    val isPopupVisible by viewModel.isPopupVisible.collectAsStateWithLifecycle()
    val selectedUsername by viewModel.selectedUsername.collectAsStateWithLifecycle()
    val selectedProfileImage by viewModel.selectedProfileImage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchPostAndComments(communityId, postId)
        viewModel.checkIfPostIsLiked(postId)
    }

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
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
                userProfileImageUrl = postUserProfileImage,
                likeCount = likeCounts[post.postId]?.toString() ?: post.likes.toString(),
                commentCount = post.commentsCount.toString(),
                comments = comments.map { comment ->
                    CommunityAnswerParams(
                        username = comment.username,
                        comment = comment.comment,
                        isOwner = comment.userId == currentUserId,
                        placeholderRes = commentUserProfiles[comment.userId],
                        onEditClick = {
                            viewModel.startEditingComment(
                                comment.commentId,
                                comment.comment
                            )
                        },
                        onDeleteClick = { viewModel.showDeletePopup(comment.commentId) },
                        onUserClick = {
                            if (comment.userId != currentUserId) {
                                viewModel.showUserPopup(
                                    userId = comment.userId,
                                    username = comment.username,
                                    profileImage = commentUserProfiles[comment.userId]
                                )
                            }
                        }
                    )
                },
                messageText = editingCommentText ?: "",
                onMessageChange = { viewModel.updateEditingCommentText(it) },
                onMessageSend = {
                    if (it.isNotBlank()) {
                        viewModel.sendOrEditComment(communityId, postId, it)
                        viewModel.updateEditingCommentText("")
                    }
                },
                onCancelEditing = { viewModel.cancelEditingComment() },
                isSaved = likedPosts[post.postId] ?: false,
                onLikeClick = { viewModel.toggleLikePost(communityId, postId) },
                onGoBackClick = onGoBackClick,
                modifier = modifier
            )
        }

        CommunityPostUiState.Init -> {}
    }

    selectedCommentId?.let { commentId ->
        DeleteContentPopUp(
            deleteTitle = "Delete Comment",
            deleteDialog = "Are you sure you want to delete this comment?",
            onDeleteClick = {
                viewModel.deleteComment(communityId, postId, commentId)
            },
            onCancelClick = { viewModel.hideDeletePopup() }
        )
    }

    if (isPopupVisible) {
        SendMessageUserPopUp(
            imageUrl = selectedProfileImage ?: "",
            username = selectedUsername ?: "",
            onSendMessageClick = {
                viewModel.selectedUserId.value?.let { otherUserId ->
                    viewModel.startChatWithUser(otherUserId) { chatId ->
                        onChatClicked(chatId)
                    }
                }
            },
            onDismissRequest = { viewModel.hideUserPopup() },
        )
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
    messageText: String,
    onMessageChange: (String) -> Unit,
    onMessageSend: (String) -> Unit,
    onCancelEditing: () -> Unit,
    onLikeClick: (Boolean) -> Unit,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    userProfileImageUrl: String? = null
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
                isSaved = isSaved,
                userProfileImageUrl = userProfileImageUrl
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
            MultipleCommunityPostComments(communityComments = comments)
        }
        CommunityPostCommentField(
            messageText = messageText,
            onMessageChange = onMessageChange,
            onMessageSend = onMessageSend,
            onCancelEditing = onCancelEditing,
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
                    comment = "comment.comment",
                    onDeleteClick = {},
                    onEditClick = {},
                    onUserClick = {}
                )
            ),
            onLikeClick = {},
            onGoBackClick = {},
            isSaved = false,
            messageText = "",
            onMessageChange = {},
            onMessageSend = {},
            onCancelEditing = {}
        )
    }
}
