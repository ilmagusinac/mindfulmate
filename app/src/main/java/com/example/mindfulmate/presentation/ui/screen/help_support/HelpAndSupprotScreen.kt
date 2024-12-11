package com.example.mindfulmate.presentation.ui.screen.help_support

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.help_support.component.HelpAndSupportHeaderSection
import com.example.mindfulmate.presentation.ui.screen.help_support.component.HelpAndSupportInformationSection
import com.example.mindfulmate.presentation.ui.screen.help_support.util.ContentRowType
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportContentType

@Composable
fun HelpAndSupportScreen(
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
            .verticalScroll(rememberScrollState())
    ) {
        HelpAndSupportHeaderSection(onGoBackClick = onGoBackClick)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_xxlarge))
                .align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        Text(
            text = stringResource(id = R.string.faq),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.general_questions),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = DuskyBlue,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        HelpAndSupportInformationSection(
            tabs = listOf(
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.general_question_q1),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.general_question_a1)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.general_question_q2),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.general_question_a2)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.general_question_q3),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.general_question_a3)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.general_question_q4),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.general_question_a4)))
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.app_features),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = DuskyBlue,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        HelpAndSupportInformationSection(
            tabs = listOf(
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.app_features_q1),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.app_features_a1)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.app_features_q2),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.app_features_a2)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.app_features_q3),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.app_features_a3)))
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.troubleshooting),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = DuskyBlue,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        HelpAndSupportInformationSection(
            tabs = listOf(
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.troubleshooting_q1),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.troubleshooting_a1)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.troubleshooting_q2),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.troubleshooting_a2)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.troubleshooting_q3),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.troubleshooting_a3)))
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.account_management),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = DuskyBlue,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        HelpAndSupportInformationSection(
            tabs = listOf(
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.account_management_q1),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.account_management_a1)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.account_management_q2),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.account_management_a2)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.account_management_q3),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.account_management_a3)))
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.privacy_security),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = DuskyBlue,
                fontWeight = FontWeight.W500
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        HelpAndSupportInformationSection(
            tabs = listOf(
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.privacy_security_q1),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.privacy_security_a1)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.privacy_security_q2),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.privacy_security_a2)))
                ),
                HelpAndSupportContentType(
                    title = stringResource(id = R.string.privacy_security_q3),
                    expandedTabs = listOf(ContentRowType(label = stringResource(id = R.string.privacy_security_a3)))
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
    }
}

@Preview
@Composable
private fun AboutAppScreenPreview() {
    MindfulMateTheme { }
}
