package com.example.mindfulmate.domain.model.help_support

data class FAQModel(
    val id: String = "",
    val title: String = "",
    val questions: List<QuestionAndAnswerFAQ> = listOf()
)

data class QuestionAndAnswerFAQ(
    val question: String = "",
    val answer: String = ""
)
