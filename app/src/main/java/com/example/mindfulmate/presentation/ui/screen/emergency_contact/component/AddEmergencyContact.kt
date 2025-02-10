package com.example.mindfulmate.presentation.ui.screen.emergency_contact.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.validatePhoneNumber

@Composable
fun AddEmergencyContact(
    contactName: TextFieldValue,
    contactPhone: TextFieldValue,
    onContactNameChange: (TextFieldValue) -> Unit,
    onContactPhoneChange: (TextFieldValue) -> Unit,
    onAddContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var phoneError by remember { mutableStateOf<String?>(null) }
    val isButtonEnabled = contactName.text.isNotBlank() && contactPhone.text.isNotBlank() && phoneError == null

    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.add_emergency_contact),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )
            )
            Spacer(modifier = modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = if (isButtonEnabled) DuskyWhite else LightGrey,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isButtonEnabled) Blue else DuskyGrey)
                    .padding(dimensionResource(id = R.dimen.padding_xsmall))
                    .clickable(enabled = isButtonEnabled) {
                        onAddContactClick()
                    }
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                .background(DuskyGrey)
        ) {
            TextField(
                value = contactName,
                placeholder = {
                    Text(
                        text = "Type Name",
                        style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                    )
                },
                onValueChange = onContactNameChange,
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
                    errorIndicatorColor = Color.Transparent
                )
            )
        }
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)))
                .background(DuskyGrey)
        ) {
            TextField(
                value = contactPhone,
                placeholder = {
                    Text(
                        text = "Type Your Emergency Contact",
                        style = MaterialTheme.typography.titleMedium.copy(color = LightGrey)
                    )
                },
                onValueChange = {
                    phoneError = it.text.validatePhoneNumber()
                    onContactPhoneChange(it)
                },
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
                    errorIndicatorColor = Color.Transparent
                )
            )
        }
        phoneError?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddEmergencyContactPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }

        AddEmergencyContact(
            contactName = textState,
            onContactNameChange = { textState = it },
            contactPhone = textState,
            onContactPhoneChange = { textState = it },
            onAddContactClick = {}
        )
    }
}
