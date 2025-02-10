package com.example.mindfulmate.presentation.ui.screen.help_support

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateSecondButton
import com.example.mindfulmate.presentation.ui.screen.help_support.component.HelpAndSupportHeaderSection
import com.example.mindfulmate.presentation.ui.screen.help_support.component.HelpAndSupportInformationSection
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportContentType
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportParams
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.view_model.help_support.HelpAndSupportUiState
import com.example.mindfulmate.presentation.view_model.help_support.HelpAndSupportViewModel

@Composable
fun HelpAndSupportScreen(
    viewModel: HelpAndSupportViewModel,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: HelpAndSupportUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showForm by remember { mutableStateOf(false) }
    var textSubjectState by remember { mutableStateOf(TextFieldValue("")) }
    var textBodyState by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchFAQ()
    }

    when (uiState) {
        is HelpAndSupportUiState.Loading -> {
            LoadingPlaceholder()
        }

        is HelpAndSupportUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        is HelpAndSupportUiState.Success -> {
            HelpAndSupportScreen(
                tabs = (uiState as HelpAndSupportUiState.Success).faq,
                onGoBackClick = onGoBackClick,
                onOpenEmailFormClick = { showForm = true },
                modifier = modifier
            )
        }

        else -> {
            // no-op
        }
    }

    if (showForm) {
        HelpAndSupportFormScreen(
            textSubject = textSubjectState,
            textBody = textBodyState,
            onConfirmationClick = {
                viewModel.sendEmail(
                    context = context,
                    emailSubject = textSubjectState.text,
                    emailBody = textBodyState.text
                )
                textSubjectState = TextFieldValue("")
                textBodyState = TextFieldValue("")
                showForm = false
            },
            onTextSubjectValueChange = { textSubjectState = it },
            onTextBodyValueChange = { textBodyState = it },
            onDismissForm = { showForm = false },
        )
    }
}

@Composable
private fun HelpAndSupportScreen(
    tabs: List<HelpAndSupportParams>,
    onGoBackClick: () -> Unit,
    onOpenEmailFormClick: () -> Unit,
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
        Text(
            text = stringResource(id = R.string.faq),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Grey,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        MindfulMateSecondButton(
            title = stringResource(id = R.string.send_email_title),
            label = stringResource(id = R.string.send_email_label),
            placeholderRes = R.drawable.ic_send_email,
            onRowClick = onOpenEmailFormClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))

        tabs.forEach { tab ->
            HelpAndSupportInformationSection(
                title = tab.title,
                tabs = tab.questions.map { question ->
                    HelpAndSupportContentType(
                        title = question.title,
                        expandedLabel = question.expandedLabel
                    )
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
    }
}

@DevicesPreview
@Composable
private fun HelpAndSupportScreenPreview() {
    MindfulMateTheme {
        HelpAndSupportScreen(
            tabs = listOf(
                HelpAndSupportParams(
                    title = "General Questions",
                    questions = listOf(
                        HelpAndSupportContentType(
                            title = "What is Mindful Mate?",
                            expandedLabel = "Mindful Mate is a mental health companion app designed to help users improve their well-being."
                        )
                    )
                ),
                HelpAndSupportParams(
                    title = "Account Management",
                    questions = listOf(
                        HelpAndSupportContentType(
                            title = "How do I delete my account?",
                            expandedLabel = "You can delete your account by navigating to the Settings page and selecting 'Delete Account'."
                        )
                    )
                )
            ),
            onGoBackClick = {},
            onOpenEmailFormClick = {}
        )
    }
}
