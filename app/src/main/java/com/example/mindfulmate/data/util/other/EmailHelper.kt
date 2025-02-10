package com.example.mindfulmate.data.util.other

import android.content.Context
import android.content.Intent
import com.example.mindfulmate.data.util.constants.HelpAndSupportFormConstant

object EmailHelper {
    fun sendEmail(
        context: Context,
        emailSubject: String,
        emailBody: String
    ){
        val intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(HelpAndSupportFormConstant.RECIEVER_EMAIL))
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        intent.putExtra(Intent.EXTRA_TEXT, emailBody)
        intent.setType("message/rfc822")

        context.startActivity(Intent.createChooser(intent, "Choose an Email client : "))
    }
}
