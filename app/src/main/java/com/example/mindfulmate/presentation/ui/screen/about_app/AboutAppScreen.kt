package com.example.mindfulmate.presentation.ui.screen.about_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Blue
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.about_app.component.AboutAppHeaderSection
import com.example.mindfulmate.presentation.ui.screen.about_app.component.AboutAppInformationSection
import com.example.mindfulmate.presentation.ui.screen.about_app.util.AboutAppContentRow
import com.example.mindfulmate.presentation.ui.screen.about_app.util.ContentRowType
import com.example.mindfulmate.presentation.util.DevicesPreview

@Composable
fun AboutAppScreen(
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
            .verticalScroll(rememberScrollState())
    ) {
        AboutAppHeaderSection(onGoBackClick = onGoBackClick)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_xxlarge))
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        AboutAppInformationSection(
            tabs = listOf(
                AboutAppContentRow(
                    title = stringResource(id = R.string.application_information),
                    expandedTabs = listOf(
                        ContentRowType(
                            title = stringResource(id = R.string.application_name),
                            label = stringResource(id = R.string.app_name)
                        ),
                        ContentRowType(
                            title = stringResource(id = R.string.application_version),
                            label = stringResource(id = R.string.application_version_label),
                        ),
                        ContentRowType(
                            title = stringResource(id = R.string.application_brief_description),
                            label = stringResource(id = R.string.application_brief_description_label),
                        )
                    ),
                    isDefaultExpanded = true
                ),
                AboutAppContentRow(
                    title = stringResource(id = R.string.team_information),
                    expandedTabs = listOf(
                        ContentRowType(
                            title = stringResource(id = R.string.ilma_gusinac_title),
                            label = stringResource(id = R.string.ilma_gusinac_label)
                        )
                    )
                ),
                AboutAppContentRow(
                    title = stringResource(id = R.string.contact_information),
                    expandedTabs = listOf(
                        ContentRowType(
                            title = stringResource(id = R.string.contact_information_email_title),
                            label = stringResource(id = R.string.contact_information_email_label)
                        )
                    )
                ),
            )
        )
    }
}

@DevicesPreview
@Composable
private fun AboutAppScreenPreview() {
    MindfulMateTheme {
        AboutAppScreen(onGoBackClick = {})
    }
}
