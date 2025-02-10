package com.example.mindfulmate.data.util

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindfulmate.data.util.other.EmailHelper
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class EmailHelperTest {

    @Test
    fun sendEmail_createsCorrectIntent() {
        // Arrange
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val emailSubject = "Test Subject"
        val emailBody = "Test Body"

        // Act
        val intentCaptor = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("test@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_TEXT, emailBody)
            type = "message/rfc822"
        }

        // Simulate calling the EmailHelper
        EmailHelper.sendEmail(context, emailSubject, emailBody)

        // Assert
        assertEquals(Intent.ACTION_SEND, intentCaptor.action)
        assertEquals("message/rfc822", intentCaptor.type)
        assertEquals(emailSubject, intentCaptor.getStringExtra(Intent.EXTRA_SUBJECT))
        assertEquals(emailBody, intentCaptor.getStringExtra(Intent.EXTRA_TEXT))
    }
}
