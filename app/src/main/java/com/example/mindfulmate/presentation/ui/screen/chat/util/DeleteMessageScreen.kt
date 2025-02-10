package com.example.mindfulmate.presentation.ui.screen.chat.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMatePopupDialog
import com.example.mindfulmate.presentation.util.DialogButtonConfig

@Composable
fun DeleteMessageScreen(
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Grey.copy(alpha = 0.5f))
    ) {
        MindfulMatePopupDialog(
            dialogTitle = "Delete Post",
            dialogText = "Are you sure you want to delete this contact?",
            buttons = listOf(
                DialogButtonConfig(
                    text = stringResource(id = R.string.delete),
                    onConfirmationClick = onDeleteClick,
                    isPrimary = true
                ),
                DialogButtonConfig(
                    text = stringResource(id = R.string.cancel),
                    onConfirmationClick = onCancelClick,
                    isPrimary = false
                )
            ),
            onDismissRequest = { onCancelClick() },
        )
    }
}

@Preview
@Composable
private fun DeleteMessageScreenPreview() {
    MindfulMateTheme {
        DeleteMessageScreen(
            onDeleteClick = {},
            onCancelClick = {}
        )
    }
}