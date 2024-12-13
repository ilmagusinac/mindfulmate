package com.example.mindfulmate.presentation.view_model.help_support

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.data.util.other.EmailHelper
import com.example.mindfulmate.domain.usecase.help_support.GetHelpAndSupportUseCase
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportContentType
import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpAndSupportViewModel @Inject constructor(
    private val getHelpAndSupportUseCase: GetHelpAndSupportUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HelpAndSupportUiState> = MutableStateFlow(HelpAndSupportUiState.Init)
    val uiState: StateFlow<HelpAndSupportUiState> = _uiState.asStateFlow()

    fun fetchFAQ() {
        viewModelScope.launch {
            _uiState.update { HelpAndSupportUiState.Loading }
            try {
                val faqs = getHelpAndSupportUseCase.invoke()
                val successState = faqs.map { faqModel ->
                    HelpAndSupportParams(
                        title = faqModel.title,
                        questions = faqModel.questions.map { question ->
                            HelpAndSupportContentType(
                                title = question.question,
                                expandedLabel = question.answer
                            )
                        }
                    )
                }
                _uiState.update { HelpAndSupportUiState.Success(faq = successState) }
            } catch (e: Exception) {
                _uiState.update {
                    HelpAndSupportUiState.Failure("Failed fetching FAQ: ${e.localizedMessage}")
                }
            }
        }
    }

    fun sendEmail(
        context: Context,
        emailSubject: String,
        emailBody: String
    ) {
        EmailHelper.sendEmail(
            context,
            emailSubject,
            emailBody
        )
    }
}
