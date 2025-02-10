package com.example.mindfulmate.presentation.util

fun transformChartData(data: List<Pair<String, Float>>): Map<String, List<Pair<String, Float>>> {
    val moodCategories = mapOf(
        "sad" to mutableListOf<Pair<String, Float>>(),
        "neutral" to mutableListOf<Pair<String, Float>>(),
        "happy" to mutableListOf<Pair<String, Float>>()
    )

    data.forEach { (date, moodScore) ->
        when {
            moodScore < 40 -> moodCategories["sad"]?.add(date to moodScore)
            moodScore in 40f..70f -> moodCategories["neutral"]?.add(date to moodScore)
            else -> moodCategories["happy"]?.add(date to moodScore)
        }
    }

    // Add a log to check the transformation result
    println("Transformed Data: $moodCategories")

    // Ensure non-empty lists
    moodCategories.forEach { (mood, list) ->
        if (list.isEmpty()) {
            list.add("Placeholder" to 0f) // Prevent empty lists
        }
    }

    return moodCategories
}

