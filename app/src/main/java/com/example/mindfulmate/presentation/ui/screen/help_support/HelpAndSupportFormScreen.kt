package com.example.mindfulmate.presentation.ui.screen.help_support

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.util.DevicesPreview

@Composable
fun HelpAndSupportFormScreen(
    textSubject: TextFieldValue,
    textBody: TextFieldValue,
    onTextSubjectValueChange: (TextFieldValue) -> Unit,
    onTextBodyValueChange: (TextFieldValue) -> Unit,
    onDismissForm: () -> Unit,
    onConfirmationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent.copy(alpha = 0.2f))
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .clickable { onDismissForm() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .shadow(
                    elevation = dimensionResource(id = R.dimen.elevation_medium),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                    clip = false
                )
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                .background(Color.White)
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(id = R.string.message_subject),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Grey,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                TextField(
                    value = textSubject,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_subject),
                            style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                        )
                    },
                    onValueChange = onTextSubjectValueChange,
                    textStyle = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                        .background(DuskyGrey),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedTextColor = Grey,
                        unfocusedTextColor = Grey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                Text(
                    text = stringResource(id = R.string.message_body),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Grey,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                TextField(
                    value = textBody,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_body),
                            style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                        )
                    },
                    onValueChange = onTextBodyValueChange,
                    textStyle = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                        .background(DuskyGrey),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedTextColor = Grey,
                        unfocusedTextColor = Grey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
                MindfulMateButton(
                    text = stringResource(id = R.string.send),
                    onClick = onConfirmationClick,
                    containerColor = Blue,
                    contentColor = DuskyWhite,
                    disabledContainerColor = Blue,
                    disabledContentColor = DuskyWhite,
                    borderColor = Blue,
                    textColor = DuskyWhite,
                    textPadding = PaddingValues(
                        horizontal = dimensionResource(id = R.dimen.padding_small),
                        vertical = dimensionResource(id = R.dimen.padding_xsmall)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@DevicesPreview
@Composable
private fun HelpAndSupportFormSectionPreview() {
    MindfulMateTheme {
        var textSubjectState by remember { mutableStateOf(TextFieldValue("")) }
        var textBodyState by remember { mutableStateOf(TextFieldValue("")) }

        HelpAndSupportFormScreen(
            textSubject = textSubjectState,
            textBody = textBodyState,
            onTextSubjectValueChange = { textSubjectState = it },
            onTextBodyValueChange = { textBodyState = it },
            onDismissForm = {},
            onConfirmationClick = {}
        )
    }
}
