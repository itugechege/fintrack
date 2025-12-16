package com.example.fintrack_mobile_apk.sms

import android.content.Context
import android.provider.Telephony
import android.util.Log

private const val TAG = "SmsReader"

class SmsReader(private val context: Context) {

    fun readSmsMessages(): List<String> {
        Log.d(TAG, "Reading SMS messages")
        val messages = mutableListOf<String>()
        val cursor = context.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val body = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.BODY))
                messages.add(body)
            }
        }

        Log.d(TAG, "Found ${messages.size} SMS messages")
        return messages
    }
}
