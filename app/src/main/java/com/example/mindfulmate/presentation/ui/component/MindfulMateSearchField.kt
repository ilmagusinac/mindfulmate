package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme

@Composable
fun MindfulMateSearchField(
    text: TextFieldValue,
    placeholder: String,
    onTextValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_medium),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                clip = false
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
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
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small))),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
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
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_xsmall))
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MindfulMateSearchFieldPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }

        MindfulMateSearchField(
            text = textState,
            onTextValueChange = { textState = it },
            placeholder = "Username"

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MindfulMateSearchFieldLeadingIconPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }

        MindfulMateSearchField(
            text = textState,
            onTextValueChange = { textState = it },
            placeholder = "Username"
        )
    }
}
