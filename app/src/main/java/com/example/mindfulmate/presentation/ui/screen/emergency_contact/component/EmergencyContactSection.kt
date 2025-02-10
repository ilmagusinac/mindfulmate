package com.example.mindfulmate.presentation.ui.screen.emergency_contact.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.util.EmergencyInformation

@Composable
fun EmergencyContactSection(
    emergencyInformation: List<EmergencyInformation>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
            .background(DuskyGrey)
    ) {
        emergencyInformation.forEach { information ->
            EmergencyContentRow(
                title = information.title,
                label = information.label
            )
        }
    }
}

@Composable
fun EmergencyContentRow(
    title: String,
    label: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
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
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_small))
                    .clickable {
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$label")
                    }
                    context.startActivity(dialIntent)
                }
            )
        }
        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.border_light),
            color = DuskyWhite
        )
    }
}

@Preview
@Composable
private fun ContentRowPreview() {
    MindfulMateTheme {
        EmergencyContentRow(
            title = "Name",
            label = "Ilma"
        )
    }
}

@Preview
@Composable
private fun EmergencyContactSectionPreview() {
    MindfulMateTheme {
        EmergencyContactSection(
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
            )
        )
    }
}
