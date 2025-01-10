package com.example.mindfulmate.data.service.daily_checkin

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DailyCheckInServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : DailyCheckInService {

    override suspend fun addCheckIn(mood: String) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val checkInData = mapOf(
            "mood" to mood,
            "date" to getCurrentDate(),
            "timestamp" to FieldValue.serverTimestamp()
        )

        firestore.collection("users")
            .document(userId)
            .collection("daily_check_ins")
            .add(checkInData)
            .await()
    }

    override suspend fun getCheckIns(): List<Pair<String, Float>> {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("daily_check_ins")
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val moodString = document.getString("mood") ?: return@mapNotNull null
            val moodScore = when (moodString.lowercase()) {
                "sad" -> 30f
                "neutral" -> 55f
                "happy" -> 80f
                else -> return@mapNotNull null
            }
            val date = document.getString("date") ?: "Unknown Date"
            date to moodScore
        }.also {
            println("Fetched check-ins: $it")
        }
    }

    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }
}
