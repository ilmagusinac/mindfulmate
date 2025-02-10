package com.example.mindfulmate.domain.model.openai

class IntentHandler(
    private val mentalHealthKeywords: List<String>
) {
    private val intents = mapOf(
        // Greetings
        "greeting" to listOf(
            "hi", "hello", "hey", "good morning", "good evening", "howdy", "what's up",
            "greetings", "hiya", "yo", "morning", "evening", "sup"
        ),
        // Farewells
        "farewell" to listOf(
            "bye", "goodbye", "see you", "take care", "later", "farewell", "adios",
            "ciao", "peace out", "catch you later", "see you soon", "till next time"
        ),
        // Gratitude
        "gratitude" to listOf(
            "thank you", "thanks", "appreciate", "grateful", "thanks a lot",
            "many thanks", "thank you so much", "cheers", "thanks a ton", "big thanks"
        ),
        // Acknowledgment
        "acknowledgment" to listOf(
            "okay", "great", "good job", "well done", "fine", "alright", "cool",
            "sounds good", "nice", "perfect", "got it", "understood", "ok", "roger that"
        ),
        // Mental Health Keywords (from mentalHealthKeywords)
        "mental_health" to mentalHealthKeywords
    )

    fun isMentalHealthRelated(message: String): Boolean {
        val tokens = message.split(" ", ".", ",", "!", "?").map { it.lowercase() }
        val result = tokens.any { token ->
            mentalHealthKeywords.any { keyword ->
                token.contains(keyword, ignoreCase = true) || keyword.contains(
                    token,
                    ignoreCase = true
                )
            }
        }
        return result
    }

    fun detectIntentWithMessage(message: String): Pair<String?, String> {
        val tokens = message.split(" ", ".", ",", "!", "?").map { it.lowercase() }
        val detectedIntent = intents.entries.firstOrNull { (_, keywords) ->
            keywords.any { keyword ->
                tokens.any {
                    it.equals(
                        keyword,
                        ignoreCase = true
                    ) || it.contains(keyword, ignoreCase = true)
                }
            }
        }?.key

        val remainingMessage = detectedIntent?.let { intent ->
            val keywords = intents[intent] ?: return@let message
            keywords.fold(message) { acc, keyword ->
                acc.replace(keyword, "", ignoreCase = true)
            }.trim()
        } ?: message

        return detectedIntent to remainingMessage
    }
}
