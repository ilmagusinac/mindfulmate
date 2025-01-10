package com.example.mindfulmate.presentation.ui.screen.profile.component.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.DuskyGrey
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateIconPlacement

@Composable
fun ProfileRowSection(
    title: String,
    label: String,
    onRowClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderRes: Int = R.drawable.ic_profile,
    tint: Color = DuskyBlue,
    isEnabled: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            /*
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_medium),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                clip = false
            )*/
            .then(
                if (isEnabled) {
                    Modifier.shadow(
                        elevation = dimensionResource(id = R.dimen.elevation_medium),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)),
                        clip = false
                    )
                } else {
                    Modifier
                }
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners_small)))
            //.background(Color.White)
            .background(if (isEnabled) Color.White else DuskyGrey)
            .clickable(enabled = isEnabled) { onRowClick() }
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_xsmall)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            MindfulMateIconPlacement(
                placeholderRes = placeholderRes,
                tint = tint
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightGrey,
                        fontSize = 10.sp
                    )
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.border_thin)))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Grey,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

            }
        }
    }
}

@Preview(
    backgroundColor = 0xFF4CB5E3,
    showBackground = true
)
@Composable
private fun ProfileRowSectionPreview() {
    MindfulMateTheme {
        ProfileRowSection(
            title = "Get your emotional analytics",
            label = "If you didn't do it already you can now!",
            onRowClick = {}
        )
    }
}
