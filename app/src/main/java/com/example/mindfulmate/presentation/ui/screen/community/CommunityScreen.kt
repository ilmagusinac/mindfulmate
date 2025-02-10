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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateFloatingActionButton
import com.example.mindfulmate.presentation.ui.screen.community.component.community.CommunityHeader
import com.example.mindfulmate.presentation.ui.screen.community.component.community.MultipleCommunityPosts
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunityPostParams
import com.example.mindfulmate.presentation.view_model.community.community.CommunityUiState
import com.example.mindfulmate.presentation.view_model.community.community.CommunityViewModel

@Composable
fun CommunityScreen(
    viewModel: CommunityViewModel,
    communityId: String,
    onBackButtonClick: () -> Unit,
    onNewPostClick: () -> Unit,
    onNavigateToPost: (String, String) -> Unit,
    onNavigateToEdit: (String, String) -> Unit,
    onChatClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: CommunityUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val posts: List<CommunityPostParams> by viewModel.posts.collectAsStateWithLifecycle()
    val isSaved: Boolean by viewModel.isCommunitySaved.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val selectedPostId by viewModel.selectedPostId.collectAsStateWithLifecycle()
    val isPopupVisible by viewModel.isPopupVisible.collectAsStateWithLifecycle()
    val selectedUsername by viewModel.selectedUsername.collectAsStateWithLifecycle()
    val selectedProfileImage by viewModel.selectedProfileImage.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(communityId) {
        viewModel.fetchCommunityDetails(
            communityId = communityId,
            navigateToPost = onNavigateToPost,
            navigateToEdit = onNavigateToEdit
        )
        viewModel.checkIfCommunityIsSaved(communityId)
    }

    when (uiState) {
        is CommunityUiState.Loading -> {
            LoadingPlaceholder()
        }

        is CommunityUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        is CommunityUiState.Success -> {
            val community = (uiState as CommunityUiState.Success).community
            CommunityScreen(
                title = community.communityName,
                membersCount = community.membersCount.toString(),
                description = community.description,
                profileImage = community.profilePicture,
                communityPosts = posts,
                onBackButtonClick = onBackButtonClick,
                onSaveStateChanged = {
                    viewModel.toggleSaveCommunity(communityId)
                },
                isSaved = isSaved,
                onNewPostClick = onNewPostClick,
                modifier = modifier
            )
        }

        CommunityUiState.Init -> {}
    }

    selectedPostId?.let { postId ->
        DeleteContentPopUp(
            deleteTitle = "Delete Post",
            deleteDialog = "Are you sure you want to delete this post?",
            onDeleteClick = {
                viewModel.deletePost(communityId, postId)
            },
            onCancelClick = { viewModel.hideDeletePopup() }
        )
    }

    if(isPopupVisible){
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
private fun CommunityScreen(
    title: String,
    membersCount: String,
    description: String,
    profileImage: String,
    isSaved: Boolean,
    communityPosts: List<CommunityPostParams>,
    onBackButtonClick: () -> Unit,
    onSaveStateChanged: (Boolean) -> Unit,
    onNewPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                    horizontal = dimensionResource(id = R.dimen.padding_default)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommunityHeader(
                title = title,
                membersCount = membersCount,
                description = description,
                onBackButtonClick = onBackButtonClick,
                onSaveStateChanged = onSaveStateChanged,
                isSaved = isSaved,
                profileImage = profileImage
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
            MultipleCommunityPosts(communityPosts = communityPosts)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_default)))
        }
        MindfulMateFloatingActionButton(
            text = stringResource(id = R.string.new_post),
            onClick = onNewPostClick,
            leadingIcon = painterResource(id = R.drawable.ic_new_post),
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.spacing_xxxxmedium))
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
private fun CommunityScreenPreview() {
    MindfulMateTheme {
        CommunityScreen(
            title = "Stress Reliefe",
            membersCount = "12",
            description = "fjrubfvrbhjndkljihugzhcfnbjnklhugzuftcgvhbjnkjigzfgvhbjnkjh oihgvbjnk ghijv fhfhfhhff ",
            profileImage = "",
            isSaved = true,
            onBackButtonClick = {},
            onSaveStateChanged = {},
            onNewPostClick = {},
            communityPosts = listOf(
                CommunityPostParams(
                    postId = "",
                    username = "post.username",
                    date = "post.date",
                    title = "post.questionTitle",
                    body = "post.questionDescription",
                    likesCount = "post.likeCount",
                    commentsCount = "post.commentCount",
                    onCommentsClick = {},
                    onEditClick = {},
                    onDeleteClick = {},
                    onUserClick = {}
                ),
                CommunityPostParams(
                    postId = "",
                    username = "post.username",
                    date = "post.date",
                    title = "post.questionTitle",
                    body = "post.questionDescription",
                    likesCount = "post.likeCount",
                    commentsCount = "post.commentCount",
                    onCommentsClick = {},
                    onEditClick = {},
                    onDeleteClick = {},
                    onUserClick = {}
                ),
                CommunityPostParams(
                    username = "post.username",
                    date = "post.date",
                    title = "post.questionTitle",
                    body = "post.questionDescription",
                    likesCount = "post.likeCount",
                    commentsCount = "post.commentCount",
                    onCommentsClick = {},
                    onEditClick = {},
                    onDeleteClick = {},
                    onUserClick = {}
                )
            )
        )
    }
}

@Preview
@Composable
private fun NoCommunityScreenPreview() {
    MindfulMateTheme {
        CommunityScreen(
            title = "Stress Reliefe",
            membersCount = "12",
            description = "oihgvbjnk ghijv fhfhfhhff ",
            profileImage = "",
            isSaved = false,
            onBackButtonClick = {},
            onSaveStateChanged = {},
            onNewPostClick = {},
            communityPosts = emptyList()
        )
    }
}
