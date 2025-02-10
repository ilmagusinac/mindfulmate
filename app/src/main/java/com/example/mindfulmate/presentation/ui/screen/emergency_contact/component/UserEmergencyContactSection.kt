package com.example.mindfulmate.presentation.ui.screen.emergency_contact.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.util.EmergencyInformation

@Composable
fun UserEmergencyContactSection(
    emergencyInformation: List<EmergencyInformation>,
    onEditClick: (EmergencyInformation) -> Unit,
    onDeleteClick: (EmergencyInformation) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if(emergencyInformation.isEmpty()){
            Text(
                text = "There are currently no contacts",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                ),
                modifier = modifier.fillMaxWidth()
            )
        } else {
            emergencyInformation.forEach { information ->
                UserEmergencyContentRow(
                    title = information.title,
                    label = information.label,
                    onEditClick = { onEditClick(information) },
                    onDeleteClick = { onDeleteClick(information) }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
            }
        }
    }
}

@Composable
fun UserEmergencyContentRow(
    title: String,
    label: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null,
                tint = Grey,
                modifier = Modifier.clickable { onEditClick() }
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xxdefault)))
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = Grey,
                modifier = Modifier.clickable { onDeleteClick() }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
                .background(DuskyGrey)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_default)
                )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier.weight(0.4f)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(0.3f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = null,
                tint = Blue,
                modifier = Modifier.clickable {
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$label")
                    }
                    context.startActivity(dialIntent)
                }
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
    }
}

@Preview
@Composable
private fun ContentRowPreview() {
    MindfulMateTheme {
        UserEmergencyContentRow(
            title = "Name",
            label = "Ilma",
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}

@Preview
@Composable
private fun EmergencyContactSectionPreview() {
    MindfulMateTheme {
        UserEmergencyContactSection(
            emergencyInformation = listOf(
                EmergencyInformation(
                    title = "Email",
                    label = "Ilma"
                ),
                EmergencyInformation(
                    title = "First Name",
                    label = "Ilma"
                ),
                EmergencyInformation(
                    title = "Number",
                    label = "061031166"
                )
            ),
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}


@Preview
@Composable
private fun EmergencyContactSectionEmptyPreview() {
    MindfulMateTheme {
        UserEmergencyContactSection(
            emergencyInformation = emptyList(),
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}
