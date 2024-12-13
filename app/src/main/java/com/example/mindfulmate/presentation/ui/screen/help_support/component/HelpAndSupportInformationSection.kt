package com.example.mindfulmate.presentation.ui.screen.help_support.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.LightGrey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateExpandedRow
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportContentType

@Composable
fun HelpAndSupportInformationSection(
    title: String,
    tabs: List<HelpAndSupportContentType>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = DuskyBlue,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        tabs.forEach { tab ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                MindfulMateExpandedRow(
                    title = tab.title,
                    isExpandable = tab.isExpandable,
                    onArrowClick = tab.onArrowClick,
                    expandedContent = {
                        Text(
                            text = tab.expandedLabel,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = LightGrey,
                                fontSize = 14.sp
                            ),
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutAppInformationSectionPreview() {
    MindfulMateTheme {
        HelpAndSupportInformationSection(
            title = "General Question",
            tabs = listOf(
                HelpAndSupportContentType(
                    title = "Privacy Policy",
                    expandedLabel = "Details about data usage"
                )
            )
        )
    }
}
