package com.example.mindfulmate.presentation.ui.screen.profile.component.edit_credential

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.ui.screen.profile.util.ContentRow
import com.example.mindfulmate.presentation.ui.screen.profile.util.ExpandedFiledType

@Composable
fun EditCredentialInformationSection(
    tabs: List<ContentRow>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_medium),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                clip = false
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
    ) {
        tabs.forEach { tab ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                EditCredentialRow(
                    title = tab.title,
                    label = tab.label,
                    placeholderRes = tab.placeholderRes,
                    tint = tab.tint,
                    isExpandable = tab.isExpandable,
                    onArrowClick = tab.onArrowClick,
                    expandedContent = {
                        tab.expandedTabs.forEach { expandedTab ->
                            ExpandedTextFields(
                                title = expandedTab.title,
                                text = expandedTab.text,
                                placeholder = expandedTab.placeholder,
                                onTextValueChange = expandedTab.onTextValueChange
                            )
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
                        MindfulMateButton(
                            onClick = tab.onButtonClick,
                            text = "Edit ${tab.buttonTitle}",
                            enabled = tab.isEnabled
                        )
                        tab.message?.let {
                            Text(
                                text = it,
                                color = Blue,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ExpandedTextFields(
    title: String,
    text: TextFieldValue,
    placeholder: String,
    onTextValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = Grey)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(White)
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
                    .padding(bottom = dimensionResource(id = R.dimen.padding_xsmall)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedTextColor = Grey,
                    unfocusedTextColor = Grey,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Blue,
                    unfocusedIndicatorColor = LightGrey,
                    errorIndicatorColor = Color.Red
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandedTextFieldsPreview() {
    MindfulMateTheme {
        ExpandedTextFields(
            title = "Text",
            text = TextFieldValue(),
            placeholder = "Enter text",
            onTextValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditCredentialInformationSectionPreview() {
    val tabs = listOf(
        ContentRow(
            title = "Username",
            label = "Enter your username",
            expandedTabs = listOf(
                ExpandedFiledType(
                    title = "First Name",
                    text = TextFieldValue(""),
                    placeholder = "Enter your first name",
                    onTextValueChange = {}
                ),
                ExpandedFiledType(
                    title = "Last Name",
                    text = TextFieldValue(""),
                    placeholder = "Enter your last name",
                    onTextValueChange = {}
                )
            ),
            buttonTitle = "Email",
            onButtonClick = {}
        ),
        ContentRow(
            title = "Password",
            label = "Enter your password",
            expandedTabs = listOf(
                ExpandedFiledType(
                    title = "Password",
                    text = TextFieldValue(""),
                    placeholder = "Enter your password",
                    onTextValueChange = {}
                )
            ),
            buttonTitle = "Password",
            onButtonClick = {}
        )
    )

    MindfulMateTheme {
        EditCredentialInformationSection(
            tabs = tabs,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
