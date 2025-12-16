package com.example.fintrack_mobile_apk.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.ui.graphics.Color

/**
 * Entity class representing a single financial account in the Room database.
 *
 * @param id The unique identifier for the account.
 * @param name The user-defined name of the account (e.g., "Checking Account").
 * @param type The type of the account, which helps categorize it (e.g., "ASSET", "LIABILITY").
 * @param balance The current balance of the account.
 * @param balanceColor The color to be used for the balance text, stored as a long.
 */
@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String,
    val balance: Double,
    val balanceColor: Long
)
