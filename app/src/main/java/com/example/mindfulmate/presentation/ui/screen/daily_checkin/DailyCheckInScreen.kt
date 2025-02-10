package com.example.mindfulmate.presentation.ui.screen.daily_checkin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.daily_checkin.component.CheckInInputSection
import com.example.mindfulmate.presentation.util.MessageModel
import com.example.mindfulmate.presentation.view_model.daily_checkin.DailyCheckInNavigationEvent
import com.example.mindfulmate.presentation.view_model.daily_checkin.DailyCheckInUiState
import com.example.mindfulmate.presentation.view_model.daily_checkin.DailyCheckInViewModel
import com.example.mindfulmate.presentation.view_model.openai.ChatViewModel

@Composable
fun DailyCheckInScreen(
    viewModel: DailyCheckInViewModel,
    chatViewModel: ChatViewModel,
    navigate: () -> Unit,
    onCancelClick: () -> Unit,
    onChatTrigger: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: DailyCheckInUiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    val triggeredMessage by viewModel.triggeredMessage.collectAsStateWithLifecycle()

    LaunchedEffect(triggeredMessage) {
        triggeredMessage?.let {
            chatViewModel.addMessage(MessageModel(message = it, role = "assistant"))
        }
    }

    when (uiState) {
        is DailyCheckInUiState.Loading -> {
            LoadingPlaceholder()
        }

        is DailyCheckInUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        else -> {
            DailyCheckInScreen(
                inputClick = { mood ->
                    viewModel.addCheckIn(mood = mood, onChatTrigger = onChatTrigger)
                },
                onCancelClick = onCancelClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: DailyCheckInViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is DailyCheckInNavigationEvent.Navigate -> {
                    navigate()
                }
            }
        }
    }
}

@Composable
private fun DailyCheckInScreen(
    inputClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMood by remember { mutableStateOf<String?>(null) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.daily_input_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Grey,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
            CheckInInputSection(
                inputClick = { mood ->
                    selectedMood = mood
                    inputClick(mood)
                }
            )
        }
        Text(
            text = stringResource(id = R.string.cancel_daily_checkin),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(id = R.dimen.padding_xxxmedium))
                .clickable { onCancelClick() }
        )
    }
}

@Preview
@Composable
private fun DailyCheckInScreenPreview() {
    MindfulMateTheme {
        DailyCheckInScreen(
            inputClick = { },
            onCancelClick = {}
        )
    }
}
