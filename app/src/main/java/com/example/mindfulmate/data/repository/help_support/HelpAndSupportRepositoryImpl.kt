package com.example.mindfulmate.data.repository.help_support

import com.example.mindfulmate.data.service.help_support.HelpAndSupportService
import com.example.mindfulmate.domain.model.help_support.FAQModel
import com.example.mindfulmate.domain.repository.help_support.HelpAndSupportRepository
import javax.inject.Inject

class HelpAndSupportRepositoryImpl @Inject constructor(
    private val helpAndSupportService: HelpAndSupportService
) : HelpAndSupportRepository {

    override suspend fun getFAQ(): List<FAQModel> =
        helpAndSupportService.getFAQ()
}
