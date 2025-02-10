package com.example.mindfulmate.presentation.ui.screen.community

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
import com.example.mindfulmate.presentation.view_model.community.community.CommunityViewModel
import com.example.mindfulmate.presentation.view_model.community.community.EditPostUiState

@Composable
fun EditPostScreen(
    viewModel: CommunityViewModel,
    communityId: String,
    postId: String,
    onGoBackClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.editPostUiState.collectAsStateWithLifecycle()

    var titleState by remember { mutableStateOf(TextFieldValue()) }
    var bodyState by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        viewModel.fetchPostForEditing(communityId, postId)
    }

    when (uiState) {
        is EditPostUiState.Loading -> {
            LoadingPlaceholder()
        }

        is EditPostUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = onGoBackClick)
        }

        is EditPostUiState.Success -> {
            val successState = uiState as EditPostUiState.Success
            if (titleState.text.isEmpty() && bodyState.text.isEmpty()) {
                titleState = TextFieldValue(successState.title)
                bodyState = TextFieldValue(successState.body)
            }

            EditPostScreen(
                title = titleState,
                body = bodyState,
                onTitleValueChange = { titleState = it },
                onBodyValueChange = { bodyState = it },
                onCloseClick = onGoBackClick,
                onEditPostClick = {
                    viewModel.editPost(
                        communityId = communityId,
                        postId = postId,
                        newTitle = titleState.text,
                        newBody = bodyState.text
                    )
                    navigate()
                },
                modifier = modifier
            )
        }

        EditPostUiState.Init -> {
            // No-op
        }
    }
}


@Composable
private fun EditPostScreen(
    title: TextFieldValue,
    onTitleValueChange: (TextFieldValue) -> Unit,
    body: TextFieldValue,
    onBodyValueChange: (TextFieldValue) -> Unit,
    onCloseClick: () -> Unit,
    onEditPostClick: () -> Unit,
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
            onPostClick = onEditPostClick,
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
private fun EditPostScreenPreview() {
    MindfulMateTheme {
        var titleState by remember { mutableStateOf(TextFieldValue("")) }
        var bodyState by remember { mutableStateOf(TextFieldValue("")) }

        EditPostScreen(
            title = titleState,
            onTitleValueChange = { titleState = it },
            body = bodyState,
            onBodyValueChange = { bodyState = it },
            onCloseClick = {},
            onEditPostClick = {},
            isPostEnabled = false
        )
    }
}
