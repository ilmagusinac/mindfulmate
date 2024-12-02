package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.DialogButtonConfig

@Composable
fun MindfulMatePopupTextField(
    dialogTitle: String,
    placeholder: String,
    text: TextFieldValue,
    buttons: List<DialogButtonConfig>,
    onTextValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null
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
                text = dialogTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Grey,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                ),
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = dimensionResource(id = R.dimen.elevation_medium),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
                        clip = false
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                    .background(Color.White)
            ) {
                TextField(
                    value = text,
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                        )
                    },
                    onValueChange = onTextValueChange,
                    textStyle = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners))),
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
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small)),
                modifier = Modifier.fillMaxWidth()
            ) {
                buttons.forEach { buttonConfig ->
                    MindfulMateButton(
                        text = buttonConfig.text,
                        onClick = buttonConfig.onConfirmationClick,
                        containerColor = if (buttonConfig.isPrimary) Blue else Color.Transparent,
                        contentColor = if (buttonConfig.isPrimary) DuskyWhite else Color.Transparent,
                        disabledContainerColor = if (buttonConfig.isPrimary) Blue else Color.Transparent,
                        disabledContentColor = if (buttonConfig.isPrimary) DuskyWhite else Color.Transparent,
                        borderColor = if (buttonConfig.isPrimary) Blue else Grey,
                        textColor = if (buttonConfig.isPrimary) DuskyWhite else Grey,
                        textPadding = PaddingValues(
                            horizontal = dimensionResource(id = R.dimen.padding_small),
                            vertical = dimensionResource(id = R.dimen.padding_xsmall)
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            message?.let {
                Text(
                    text = it,
                    color = Blue,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MindfulMatePopupTextFieldPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }


        MindfulMatePopupTextField(
            dialogTitle = "Reset Password",
            placeholder = "Enter your password",
            text = textState,
            buttons = listOf(
                DialogButtonConfig(
                    text = "RESTART",
                    onConfirmationClick = {},
                    isPrimary = true
                )
            ),
            onTextValueChange = { textState = it },
        )
    }
}
