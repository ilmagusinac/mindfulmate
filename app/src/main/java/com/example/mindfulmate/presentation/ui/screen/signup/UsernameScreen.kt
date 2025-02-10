package com.example.mindfulmate.presentation.ui.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMatePopupTextField
import com.example.mindfulmate.presentation.util.DialogButtonConfig


@Composable
fun UsernameScreen(
    username: TextFieldValue,
    onConfirmationClick: () -> Unit,
    onBackClick: () -> Unit,
    onUsernameValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    usernameMessageCheck: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = stringResource(id = R.string.logo_content_description),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        MindfulMatePopupTextField(
            dialogTitle = stringResource(id = R.string.enter_username),
            placeholder = stringResource(id = R.string.enter_your_username),
            text = username,
            buttons = listOf(
                DialogButtonConfig(
                    text = stringResource(id = R.string.back),
                    onConfirmationClick = onConfirmationClick,
                    isPrimary = false
                ),
                DialogButtonConfig(
                    text = stringResource(id = R.string.next),
                    onConfirmationClick = onBackClick,
                    isPrimary = true
                )
            ),
            onTextValueChange = onUsernameValueChange,
            message = usernameMessageCheck
        )
    }
}

@Preview
@Composable
private fun UsernameScreenPreview() {
    MindfulMateTheme {
        var resetPasswordState by remember { mutableStateOf(TextFieldValue("")) }

        UsernameScreen(
            username = resetPasswordState,
            onConfirmationClick = {},
            onBackClick = {},
            onUsernameValueChange = { resetPasswordState = it },
            //usernameMessageCheck = "Error"
        )
    }
}
