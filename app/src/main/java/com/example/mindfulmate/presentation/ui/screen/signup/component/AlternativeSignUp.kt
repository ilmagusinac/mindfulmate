package com.example.mindfulmate.presentation.ui.screen.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton

@Composable
fun AlternativeSignUp(
    onSignUpWithGoogleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.border_thin)),
                color = Grey
            )
            Text(
                text = stringResource(id = R.string.or),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W200
                ),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_xsmall))
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(dimensionResource(id = R.dimen.border_thin)),
                color = Grey
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = stringResource(id = R.string.google_content_description),
                tint = Blue
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_default)))
            MindfulMateButton(
                onClick = onSignUpWithGoogleClick,
                text = stringResource(id = R.string.signup_with_google),
                textPadding = PaddingValues(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_xlarge)
                ),
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                borderColor = Grey,
                textColor = Grey
            )
        }
    }
}

@Preview
@Composable
private fun AlternativeSignInPreview() {
    MindfulMateTheme {
        AlternativeSignUp(onSignUpWithGoogleClick = {})
    }
}
