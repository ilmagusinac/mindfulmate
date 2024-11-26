package com.example.mindfulmate.data.util.constants

import com.example.mindfulmate.data.network.openai.ChatMessage

object OpenAIValues {
    const val BASE_URL = "https://api.openai.com/"
    const val API_KEY = "Bearer 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"
    const val MODEL_NAME = "gpt-3.5-turbo"
}

object SystemMessage{
    val SYSTEM_MESSAGE = ChatMessage(
        role = "system",
        content = """
        You are an AI mental health assistant. Your purpose is to provide guidance and support related to mental health topics such as anxiety, depression, stress management, coping strategies, therapy, and emotional well-being. 
        If the user asks a question that is not related to mental health, politely inform them that you can only provide advice related to mental health.
        """.trimIndent()
    )
}

object MentalHealthKeywords {
    val MENTAL_HEALTH_KEYWORDS = listOf(
        // General Mental Health Topics
        "mental health", "mental illness", "mental well-being", "psychology",
        "psychiatry", "emotional health", "well-being", "psychotherapy",
        "mental wellness", "mental stability", "mental fitness", "behavioral health",

        // Therapy and Counseling
        "therapy", "therapist", "counseling", "counselor", "psychologist",
        "psychiatrist", "CBT", "cognitive behavioral therapy", "group therapy",
        "family therapy", "trauma therapy", "online therapy", "support group",

        // Emotions and Emotional States
        "emotions", "emotional well-being", "feelings", "sadness", "happiness",
        "grief", "loss", "loneliness", "overwhelmed", "guilt", "shame",
        "anger", "frustration", "irritation", "jealousy", "envy", "joy",
        "fear", "panic", "nervousness", "excitement", "contentment",
        "anxiety", "worry", "apprehension", "hopefulness", "optimism",
        "depression", "despair", "hopelessness", "helplessness", "trauma",

        // Self-Care and Coping
        "self-care", "coping", "coping mechanisms", "relaxation",
        "mindfulness", "meditation", "yoga", "journaling",
        "deep breathing", "progressive muscle relaxation", "self-reflection",
        "self-compassion", "self-love", "self-esteem", "self-worth",
        "resilience", "mental toughness", "emotional regulation",

        // Disorders and Diagnoses
        "bipolar disorder", "schizophrenia", "OCD", "obsessive-compulsive disorder",
        "PTSD", "post-traumatic stress disorder", "eating disorder", "anorexia",
        "bulimia", "binge eating disorder", "addiction", "substance abuse",
        "alcoholism", "ADHD", "attention deficit hyperactivity disorder",
        "autism spectrum disorder", "ASD", "generalized anxiety disorder",
        "panic disorder", "social anxiety disorder", "specific phobias",

        // Stress and Burnout
        "stress", "stress relief", "burnout", "chronic stress",
        "stress management", "work stress", "academic stress",
        "financial stress", "relationship stress", "coping with stress",
        "overwhelm", "time management",

        // Relationships and Communication
        "relationships", "relationship advice", "friendships",
        "family relationships", "romantic relationships", "marriage counseling",
        "breakups", "conflict resolution", "communication skills",
        "active listening", "boundaries", "healthy boundaries",

        // Sleep and Rest
        "sleep", "insomnia", "sleep hygiene", "restful sleep",
        "sleep disorders", "sleep apnea", "rest", "naps",
        "circadian rhythm", "dreams",

        // Physical and Mental Connection
        "exercise", "fitness", "physical health", "nutrition",
        "healthy eating", "hydration", "body image", "weight loss",
        "body positivity", "holistic health", "mind-body connection",

        // Work and Productivity
        "productivity", "work-life balance", "burnout prevention",
        "career stress", "focus", "concentration", "motivation",
        "goal setting", "time management",

        // Life Events and Transitions
        "life changes", "adjustment", "new job", "relocation",
        "divorce", "retirement", "graduation", "starting college",
        "empty nest", "parenting stress",

        // Trauma and Recovery
        "trauma", "healing", "recovery", "childhood trauma",
        "trauma recovery", "flashbacks", "emotional healing",
        "trauma-informed care",

        // Positive Psychology and Growth
        "gratitude", "positive psychology", "growth mindset",
        "happiness", "contentment", "joy", "fulfillment",
        "self-actualization", "personal growth", "life purpose",
        "meaning of life", "mental clarity", "peace of mind",

        // Bullying and Abuse
        "bullying", "harassment", "emotional abuse", "physical abuse",
        "sexual abuse", "domestic violence", "toxic relationships",
        "narcissistic abuse", "gaslighting", "manipulation",

        // Crisis and Help-Seeking
        "suicide prevention", "suicidal thoughts", "crisis hotline",
        "mental health resources", "helpline", "mental health app",
        "online support", "mental health awareness",

        // Other Common Keywords For Mental Health
        "calmness", "tranquility", "peace", "acceptance",
        "forgiveness", "hope", "therapy session", "mental clarity",
        "focus exercises", "work stress relief", "emotional support",
        "community support", "life coaching", "habit change"
    )
}
