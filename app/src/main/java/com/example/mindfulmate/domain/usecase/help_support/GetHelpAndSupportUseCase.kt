package com.example.mindfulmate.domain.usecase.help_support

import com.example.mindfulmate.domain.model.help_support.FAQModel
import com.example.mindfulmate.domain.repository.help_support.HelpAndSupportRepository
import javax.inject.Inject

class GetHelpAndSupportUseCase @Inject constructor(
    private val helpAndSupportRepository: HelpAndSupportRepository
) {
    suspend operator fun invoke(): List<FAQModel> {
        return helpAndSupportRepository.getFAQ()
    }
}
