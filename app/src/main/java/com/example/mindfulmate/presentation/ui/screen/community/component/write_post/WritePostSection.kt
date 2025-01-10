package com.example.mindfulmate.presentation.ui.screen.community.component.write_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun WritePostSection(
    title: TextFieldValue,
    onTitleValueChange: (TextFieldValue) -> Unit,
    body: TextFieldValue,
    onBodyValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        WritePostTextField(
            text = title,
            onTextValueChange = onTitleValueChange,
            placeholder = stringResource(id = R.string.title),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            textFieldTextStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.border_light),
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_default))
        )
        WritePostTextField(
            text = body,
            onTextValueChange = onBodyValueChange,
            placeholder = stringResource(id = R.string.body_text)

        )
    }
}

@Composable
fun WritePostTextField(
    text: TextFieldValue,
    placeholder: String,
    onTextValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(color = Grey),
    textFieldTextStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        TextField(
            value = text,
            placeholder = {
                Text(
                    text = placeholder,
                    style = textStyle
                )
            },
            onValueChange = onTextValueChange,
            textStyle = textFieldTextStyle,
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
                errorIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
private fun WritePostTextFieldPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }

        WritePostTextField(
            text = textState,
            onTextValueChange = { textState = it },
            placeholder = "Username"

        )
    }
}

@Preview
@Composable
private fun WritePostSectionPreview() {
    MindfulMateTheme {
        var titleState by remember { mutableStateOf(TextFieldValue("")) }
        var bodyState by remember { mutableStateOf(TextFieldValue("")) }

        WritePostSection(
            title = titleState,
            onTitleValueChange = { titleState = it },
            body = bodyState,
            onBodyValueChange = { bodyState = it }
        )
    }
}
