package com.example.mindfulmate.presentation.ui.screen.about_app.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.mindfulmate.presentation.ui.screen.about_app.util.AboutAppContentRow
import com.example.mindfulmate.presentation.ui.screen.about_app.util.ContentRowType

@Composable
fun AboutAppInformationSection(
    tabs: List<AboutAppContentRow>,
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
                AboutAppRow(
                    title = tab.title,
                    isExpandable = tab.isExpandable,
                    onArrowClick = tab.onArrowClick,
                    expandedContent = {
                        tab.expandedTabs.forEach { expandedTab ->
                            ContentRow(
                                title = expandedTab.title,
                                label = expandedTab.label
                            )
                        }
                    },
                    isDefaultExpanded = tab.isDefaultExpanded
                )
            }
        }
    }
}

@Composable
fun ContentRow(
    title: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.padding_default),
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium)
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
                    fontSize = 13.sp
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(0.6f)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_default)))
        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.border_light),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_default))
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

@Preview(showBackground = true)
@Composable
private fun AboutAppInformationSectionPreview() {
    MindfulMateTheme {
        AboutAppInformationSection(
            tabs = listOf(
                AboutAppContentRow(
                    title = "App Version",
                    expandedTabs = emptyList()
                ),
                AboutAppContentRow(
                    title = "Privacy Policy",
                    expandedTabs = listOf(
                        ContentRowType(
                            title = "Data Collection",
                            label = "Details about data usage"
                        ),
                        ContentRowType(
                            title = "Permissions",
                            label = "Details about permissions"
                        )
                    )
                )
            )
        )
    }
}
