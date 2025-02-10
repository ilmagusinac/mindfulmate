package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.DialogButtonConfig

@Composable
fun ErrorPlaceholder(
    onConfirmationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = stringResource(id = R.string.logo_content_description),
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_xxmedium),
                bottom = dimensionResource(id = R.dimen.spacing_xxxlarge)
            )
        )
        MindfulMateInlinePopupDialog(
            dialogTitle = stringResource(id = R.string.error_title),
            dialogText = stringResource(id = R.string.error_text),
            buttons = listOf(
                DialogButtonConfig(
                    text = stringResource(id = R.string.reset),
                    onConfirmationClick = onConfirmationClick,
                    isPrimary = true
                )
            ),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_xxxlarge))
        )
    }
}

@Preview
@Composable
private fun ErrorPlaceholderPreview() {
    MindfulMateTheme {
        ErrorPlaceholder(
            onConfirmationClick = {}
        )
    }

}
