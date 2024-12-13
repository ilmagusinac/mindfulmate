package com.example.mindfulmate.data.service.help_support

import com.example.mindfulmate.domain.model.help_support.FAQModel

interface HelpAndSupportService {
    suspend fun getFAQ(): List<FAQModel>
}
