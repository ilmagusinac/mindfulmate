package com.example.mindfulmate.presentation.work.daily_checkin

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class CheckInStateManager(private val context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val LAST_CHECK_IN_DATE = stringPreferencesKey("last_check_in_date")
    }

    @TargetApi(Build.VERSION_CODES.O)
    val isCheckInForTodayPending: Flow<Boolean> = flow {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            emitAll(dataStore.data.map { preferences ->
                val lastCheckInDate = preferences[LAST_CHECK_IN_DATE]?.let { LocalDate.parse(it) }
                lastCheckInDate != LocalDate.now()
            })
        } else {
            emit(false)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    suspend fun setCheckInCompleted() {
        dataStore.edit { preferences ->
            preferences[LAST_CHECK_IN_DATE] = LocalDate.now().toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun cancelTodayCheckIn() {
        dataStore.edit { preferences ->
            preferences[LAST_CHECK_IN_DATE] = LocalDate.now().toString()
        }
    }

}
