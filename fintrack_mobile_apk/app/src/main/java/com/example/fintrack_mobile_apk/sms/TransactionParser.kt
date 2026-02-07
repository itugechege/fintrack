package com.example.fintrack_mobile_apk.sms

import com.example.fintrack_mobile_apk.data.Transaction

class TransactionParser {

    private val amountRegex = "\\d+\\.\\d{2}|\\d+".toRegex()
    private val merchantRegex = "(?:at|to|from) ([A-Za-z ]+)".toRegex()

    fun parse(message: String): Transaction? {
        val amount = amountRegex.find(message)?.value?.toDoubleOrNull()
        val merchant = merchantRegex.find(message)?.groupValues?.get(1)?.trim()

        if (amount != null && merchant != null) {
            val category = categorize(merchant, message)
            return Transaction(
                name = merchant,
                date = System.currentTimeMillis(),
                category = category,
                amount = if (message.contains("spent") || message.contains("debited")) -amount else amount,
                needsClarification = category == "uncategorized"
            )
        }
        return null
    }

    private fun categorize(merchant: String, message: String): String {
        return when {
            merchant.contains("Safaricom") -> "Airtime"
            merchant.contains("KPLC") -> "Utilities"
            merchant.contains("Zuku") -> "Internet"
            message.contains("rent") -> "Rent"
            else -> "uncategorized"
        }
    }
}
