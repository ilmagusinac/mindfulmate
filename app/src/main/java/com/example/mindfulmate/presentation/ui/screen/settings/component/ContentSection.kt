package com.example.mindfulmate.presentation.ui.screen.settings.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.settings.util.ContentRow

@Composable
fun ContentSection(
    tabs: List<ContentRow>,
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
        tabs.forEach { tab ->
            ContentRow(
                title = tab.title,
                label = tab.label,
                onRowIconClick = tab.onRowClick,
                placeholderRes = tab.placeholderRes,
                tint = tab.tint,
                rowType = tab.rowType,
                switchState = tab.switchState ?: false
            )
        }
    }
}

@Preview
@Composable
private fun ContentSectionPreview() {
    MindfulMateTheme {
        ContentSection(
            tabs = listOf(
                ContentRow(
                    title = "My Account",
                    label = "Make changes to your account",
                    onRowClick = {},
                    placeholderRes = R.drawable.ic_profile
                ),
                ContentRow(
                    title = "Privacy Settings",
                    label = "Adjust your privacy preferences",
                    onRowClick = {},
                    placeholderRes = R.drawable.ic_profile
                )
            )
        )
    }
}
