package com.example.mindfulmate.presentation.ui.screen.profile.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileInformation

@Composable
fun ProfileInformationSection(
    profileInformation: List<ProfileInformation>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_medium),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                clip = false
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
    ) {
        profileInformation.forEach { information ->
            ContentRow(
                title = information.title,
                label = information.label
            )
        }
    }
}

@Composable
fun ContentRow(
    title: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
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
                    color = LightGrey,
                    fontSize = 14.sp
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
                modifier = Modifier.weight(0.6f)
            )
        }
        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.border_light)
        )
    }
}

@Preview
@Composable
private fun ContentRowPreview() {
    MindfulMateTheme {
        ContentRow(
            title = "Name",
            label = "Ilma",
        )
    }
}

@Preview
@Composable
private fun ProfileInformationSectionPreview() {
    MindfulMateTheme {
        ProfileInformationSection(
            profileInformation = listOf(
                ProfileInformation(
                    title = "Email",
                    label = "Ilma"
                ),
                ProfileInformation(
                    title = "First Name",
                    label = "Ilma"
                ),
                ProfileInformation(
                    title = "Number",
                    label = "Ilma"
                )
            )
        )
    }
}
