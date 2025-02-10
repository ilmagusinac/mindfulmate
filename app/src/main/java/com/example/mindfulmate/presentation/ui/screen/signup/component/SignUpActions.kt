package com.example.mindfulmate.presentation.ui.screen.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton

@Composable
fun SignUpActions(
    isSignUpEnabled: Boolean,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MindfulMateButton(
            onClick = onSignUpClick,
            text = stringResource(id = R.string.signup),
            enabled = isSignUpEnabled
        )
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.already_have_an_account),
                style = MaterialTheme.typography.titleMedium.copy(color = Grey)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
            MindfulMateButton(
                onClick = onSignInClick,
                text = stringResource(id = R.string.signin),
                textPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_xsmall)
                ),
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                borderColor = Grey,
                textColor = Grey,
                textStyle = MaterialTheme.typography.labelSmall.copy(color = Grey)
            )
        }
    }
}

@Preview
@Composable
private fun SignInActionsPreview() {
    MindfulMateTheme {
        SignUpActions(
            onSignUpClick = {},
            onSignInClick = {},
            isSignUpEnabled = true
        )
    }
}
