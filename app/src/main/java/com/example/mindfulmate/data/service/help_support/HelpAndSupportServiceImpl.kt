package com.example.mindfulmate.data.service.help_support

import com.example.mindfulmate.domain.model.help_support.FAQModel
import com.example.mindfulmate.domain.model.help_support.QuestionAndAnswerFAQ
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HelpAndSupportServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): HelpAndSupportService {

    override suspend fun getFAQ(): List<FAQModel> {
        val faqCollection = firestore.collection("faq")
            .get()
            .await()


        return faqCollection.documents.map { document ->
            val title = document.getString("title") ?: "Untitled Section"
            val questions = document.get("questions") as? List<Map<String, String>> ?: emptyList()
            val mappedQuestions = questions.map { question ->
                QuestionAndAnswerFAQ(
                    question = question["question"] ?: "Untitled Question",
                    answer = question["answer"] ?: "No Answer"
                )
            }
            FAQModel(
                title = title,
                questions = mappedQuestions
            )
        }
    }
}
