package com.example.mindfulmate.presentation.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateFloatingActionButton
import com.example.mindfulmate.presentation.ui.component.MindfulMatePartialBottomSheet
import com.example.mindfulmate.presentation.ui.screen.community.util.CommunitySectionParams
import com.example.mindfulmate.presentation.ui.screen.home.component.CommunityRow
import com.example.mindfulmate.presentation.ui.screen.home.component.HeaderWithComponents
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.util.MessageModel
import com.example.mindfulmate.presentation.view_model.community.community_home.CommunityHomeViewModel
import com.example.mindfulmate.presentation.view_model.nav_graph.NavGraphViewModel
import com.example.mindfulmate.presentation.view_model.openai.ChatViewModel

@Composable
fun HomeScreen(
    viewModel: ChatViewModel,
    navGraphViewModel: NavGraphViewModel,
    communityViewModel: CommunityHomeViewModel,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCommunityClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val messageList by viewModel.messages.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()
    val profileImage by viewModel.profileImage.collectAsStateWithLifecycle()
    val showBottomSheet by navGraphViewModel.showBottomSheet.collectAsStateWithLifecycle()
    val triggeredMessage by navGraphViewModel.triggeredMessage.collectAsStateWithLifecycle()
    val topCommunities by communityViewModel.topCommunitiesRow.collectAsStateWithLifecycle()
    val userCommunities by communityViewModel.userCommunitiesRow.collectAsStateWithLifecycle()

    LaunchedEffect(triggeredMessage) {
        triggeredMessage?.let {
            viewModel.addMessage(MessageModel(it, "assistant"))
            navGraphViewModel.setBottomSheetState(true, null)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUser()
        viewModel.loadUserProfileImage()
        communityViewModel.fetchTopCommunities(onCommunityClick)
        communityViewModel.getUserCommunities(onCommunityClick)
        communityViewModel.fetchCommunitiesSearch()
    }

    HomeScreen(
        messageList = messageList,
        username = username ?: "user",
        profileImage = profileImage ?: "",
        topCommunities = topCommunities,
        myCommunities = userCommunities,
        onMessageSend = { message -> viewModel.sendMessage(message) },
        onMenuClick = onMenuClick,
        onProfileClick = onProfileClick,
        onBottomSheetClick = {
            navGraphViewModel.setBottomSheetState(
                true,
                null
            )
        },
        showBottomSheet = showBottomSheet,
        onDismissBottomSheet = { navGraphViewModel.dismissBottomSheet() },
        modifier = modifier
    )
}

@Composable
private fun HomeScreen(
    messageList: List<MessageModel>,
    username: String,
    profileImage: String,
    topCommunities: List<CommunitySectionParams>,
    myCommunities: List<CommunitySectionParams>,
    onMessageSend: (String) -> Unit,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    onBottomSheetClick: () -> Unit,
    showBottomSheet: Boolean,
    onDismissBottomSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderWithComponents(
                username = username,
                profileImage = profileImage,
                onMenuClick = onMenuClick,
                onProfileClick = onProfileClick
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_default))
            ) {
                CommunityRow(
                    title = "Top Communities",
                    communities = topCommunities
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
                CommunityRow(
                    title = "Your Communities",
                    communities = myCommunities
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
            }
        }

        MindfulMateFloatingActionButton(
            text = stringResource(id = R.string.chat_with_mate),
            onClick = onBottomSheetClick,
            leadingIcon = painterResource(id = R.drawable.ic_chat),
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
    if (showBottomSheet) {
        MindfulMatePartialBottomSheet(
            showBottomSheet = showBottomSheet,
            onDismissRequest = onDismissBottomSheet,
            messageList = messageList,
            onMessageSend = onMessageSend
        )
    }
}

@DevicesPreview
@Composable
private fun HomeScreenPreview() {
    MindfulMateTheme {
        HomeScreen(
            messageList = listOf(),
            username = "username",
            profileImage = "",
            onMessageSend = {},
            onMenuClick = {},
            onProfileClick = {},
            onBottomSheetClick = {},
            showBottomSheet = true,
            onDismissBottomSheet = {},
            topCommunities = emptyList(),
            myCommunities = emptyList()
        )
    }
}
