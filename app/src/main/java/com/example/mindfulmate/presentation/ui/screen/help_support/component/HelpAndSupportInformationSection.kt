package com.example.mindfulmate.presentation.ui.screen.help_support.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.help_support.util.ContentRowType
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportContentType

@Composable
fun HelpAndSupportInformationSection(
    tabs: List<HelpAndSupportContentType>,
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
                HelpAndSupportRow(
                    title = tab.title,
                    isExpandable = tab.isExpandable,
                    onArrowClick = tab.onArrowClick,
                    expandedContent = {
                        tab.expandedTabs.forEach { expandedTab ->
                            ContentRow(
                                label = expandedTab.label
                            )
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ContentRow(
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.padding_default),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LightGrey,
                fontSize = 14.sp
            )
        )
    }
}

@Preview
@Composable
private fun ContentRowPreview() {
    MindfulMateTheme {
        ContentRow(
            label = "Name",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutAppInformationSectionPreview() {
    MindfulMateTheme {
        HelpAndSupportInformationSection(
            tabs = listOf(
                HelpAndSupportContentType(
                    title = "App Version",
                    expandedTabs = emptyList()
                ),
                HelpAndSupportContentType(
                    title = "Privacy Policy",
                    expandedTabs = listOf(
                        ContentRowType(
                            label = "Details about data usage"
                        ),
                        ContentRowType(
                            label = "Details about permissions"
                        )
                    )
                )
            )
        )
    }
}
