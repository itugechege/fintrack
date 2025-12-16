package com.example.fintrack_mobile_apk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a single financial transaction in the Room database.
 *
 * @param id The unique identifier for the transaction.
 * @param name The name or description of the transaction (e.g., "Monthly Salary").
 * @param date The date the transaction occurred.
 * @param category The category of the transaction (e.g., "Rent Payment").
 * @param amount The value of the transaction.
 */
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val date: Long,
    val category: String,
    val amount: Double
)
