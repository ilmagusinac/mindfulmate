package com.example.mindfulmate.domain.repository.help_support

import com.example.mindfulmate.domain.model.help_support.FAQModel

interface HelpAndSupportRepository {
    suspend fun getFAQ(): List<FAQModel>
}
