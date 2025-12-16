package com.example.fintrack_mobile_apk.sms

import android.content.Context
import com.example.fintrack_mobile_apk.data.FintrackRepository

class SmsService(context: Context, private val repository: FintrackRepository) {

    private val smsReader = SmsReader(context)
    private val transactionParser = TransactionParser()

    suspend fun importSmsTransactions() {
        val messages = smsReader.readSmsMessages()
        for (message in messages) {
            val transaction = transactionParser.parse(message)
            if (transaction != null) {
                repository.insertTransaction(transaction)
            }
        }
    }
}
