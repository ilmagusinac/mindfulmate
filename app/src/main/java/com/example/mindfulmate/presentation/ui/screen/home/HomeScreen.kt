package com.example.mindfulmate.presentation.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.ui.component.MindfulMatePartialBottomSheet
import com.example.mindfulmate.presentation.ui.screen.home.component.Community
import com.example.mindfulmate.presentation.ui.screen.home.component.CommunityRow
import com.example.mindfulmate.presentation.ui.screen.home.component.HeaderWithComponents
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.util.MessageModel
import com.example.mindfulmate.presentation.view_model.openai.ChatViewModel
import com.example.mindfulmate.presentation.view_model.profile.ProfileViewModel

@Composable
fun HomeScreen(
    viewModel: ChatViewModel,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val messageList by viewModel.messages.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    HomeScreen(
        messageList = messageList,
        username = username ?: "user",
        onMessageSend = { message -> viewModel.sendMessage(message) },
        onMenuClick = onMenuClick,
        onProfileClick = onProfileClick,
        modifier = modifier
    )
}

@Composable
private fun HomeScreen(
    messageList: List<MessageModel>,
    username: String,
    onMessageSend: (String) -> Unit,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var textState by remember { mutableStateOf(TextFieldValue("")) }
        val sampleCommunities = listOf(
            Community(
                title = "Anxiety Management",
                membersCount = "23,600",
                backgroundImage = R.drawable.ic_splash,
                logo = R.drawable.ic_google
            ),
            Community(
                title = "Stress Relief",
                membersCount = "7,600",
                backgroundImage = R.drawable.ic_launcher_background,
                logo = R.drawable.ic_heart
            ),
            Community(
                title = "Mindfulness",
                membersCount = "18,200",
                backgroundImage = R.drawable.ic_splash,
                logo = R.drawable.ic_resources
            )
        )

        HeaderWithComponents(
            username = username,
            searchFieldValue = textState,
            onSearchFieldValueChange = { textState = it },
            onMenuClick = onMenuClick,
            onProfileClick = onProfileClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_default))
        ) {
            CommunityRow(
                title = "Top Communities",
                communities = sampleCommunities
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
            CommunityRow(
                title = "Your Communities",
                communities = sampleCommunities
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
            MindfulMateButton(
                onClick = { showBottomSheet = true },
                text = stringResource(id = R.string.chat_with_mate),
                textPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_xsmall)
                ),
                textStyle = MaterialTheme.typography.labelSmall.copy(color = DuskyWhite),
                leadingIcon = painterResource(id = R.drawable.ic_chat)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        }
    }
    MindfulMatePartialBottomSheet(
        showBottomSheet = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        messageList = messageList,
        onMessageSend = onMessageSend
    )
}

@DevicesPreview
@Composable
private fun HomeScreenPreview() {
    MindfulMateTheme {
        HomeScreen(
            messageList = listOf(),
            username = "username",
            onMessageSend = {},
            onMenuClick = {},
            onProfileClick = {}
        )
    }
}
