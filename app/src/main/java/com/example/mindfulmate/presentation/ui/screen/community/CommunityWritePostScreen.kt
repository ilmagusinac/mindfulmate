package com.example.mindfulmate.presentation.ui.screen.community

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.community.component.write_post.WritePostHeader
import com.example.mindfulmate.presentation.ui.screen.community.component.write_post.WritePostSection
import com.example.mindfulmate.presentation.view_model.community.community_write_post.CommunityWritePostNavigationEvent
import com.example.mindfulmate.presentation.view_model.community.community_write_post.CommunityWritePostUiState
import com.example.mindfulmate.presentation.view_model.community.community_write_post.CommunityWritePostViewModel

@Composable
fun CommunityWritePostScreen(
    viewModel: CommunityWritePostViewModel,
    communityId: String,
    onCloseClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: CommunityWritePostUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var titleState by remember { mutableStateOf(TextFieldValue()) }
    var bodyState by remember { mutableStateOf(TextFieldValue()) }
    val isPostEnabled by viewModel.isPostEnabled.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    when (uiState) {
        is CommunityWritePostUiState.Loading -> {
            LoadingPlaceholder()
        }
        is CommunityWritePostUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }
        else -> {
            CommunityWritePostScreen(
                title = titleState,
                onTitleValueChange = {
                    titleState = it
                    viewModel.validatePost(it.text, bodyState.text)
                },
                body = bodyState,
                onBodyValueChange = {
                    bodyState = it
                    viewModel.validatePost(titleState.text, it.text)
                },
                onCloseClick = onCloseClick,
                onPostClick = {
                    viewModel.createPost(communityId, titleState.text, bodyState.text)
                },
                isPostEnabled = isPostEnabled,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: CommunityWritePostViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is CommunityWritePostNavigationEvent.Navigate -> {
                    navigate()
                }

                null -> TODO()
            }
        }
    }
}

@Composable
private fun CommunityWritePostScreen(
    title: TextFieldValue,
    onTitleValueChange: (TextFieldValue) -> Unit,
    body: TextFieldValue,
    onBodyValueChange: (TextFieldValue) -> Unit,
    onCloseClick: () -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPostEnabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
    ) {
        WritePostHeader(
            onCloseClick = onCloseClick,
            onPostClick = onPostClick,
            enabled = isPostEnabled
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_default)))
        WritePostSection(
            title = title,
            onTitleValueChange = onTitleValueChange,
            body = body,
            onBodyValueChange = onBodyValueChange
        )
    }
}

@Preview
@Composable
private fun WritePostScreenPreview() {
    MindfulMateTheme {
        var titleState by remember { mutableStateOf(TextFieldValue("")) }
        var bodyState by remember { mutableStateOf(TextFieldValue("")) }

        CommunityWritePostScreen(
            title = titleState,
            onTitleValueChange = { titleState = it },
            body = bodyState,
            onBodyValueChange = { bodyState = it },
            onCloseClick = {},
            onPostClick = {},
            isPostEnabled = false
        )
    }
}
