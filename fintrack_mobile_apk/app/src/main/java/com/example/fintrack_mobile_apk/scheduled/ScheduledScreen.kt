package com.example.fintrack_mobile_apk.scheduled

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "ScheduledScreen"

/**
 * Data class representing a single scheduled transaction.
 * This structure holds the information for each recurring transaction displayed on the [ScheduledScreen].
 *
 * @param name The name of the scheduled transaction (e.g., "Monthly Rent").
 * @param frequency A description of how often the transaction occurs (e.g., "Rent payment").
 * @param nextDate The date of the next occurrence, formatted as a string.
 * @param amount The value of the transaction, formatted as a string.
 * @param drAccount The debit account for the transaction.
 * @param crAccount The credit account for the transaction.
 */
data class ScheduledTransaction(
    val name: String,
    val frequency: String,
    val nextDate: String,
    val amount: String,
    val drAccount: String,
    val crAccount: String
)

/**
 * Composable function for the Scheduled screen.
 * This screen displays a list of the user's scheduled transactions using a [LazyColumn].
 * It is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.SCHEDULED] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 */
@Composable
fun ScheduledScreen(modifier: Modifier = Modifier) {
    Log.d(TAG, "ScheduledScreen: Composing")
    // Placeholder data for the list of scheduled transactions. In a real app, this would be fetched from a ViewModel or repository.
    val scheduledTransactions = listOf(
        ScheduledTransaction(
            name = "Monthly Rent",
            frequency = "Rent payment",
            nextDate = "Next: Jan 01, 2025",
            amount = "$1,500.00",
            drAccount = "Rent",
            crAccount = "Checking Account"
        ),
        ScheduledTransaction(
            name = "Salary Deposit",
            frequency = "Monthly salary",
            nextDate = "Next: Jan 01, 2025",
            amount = "$6,500.00",
            drAccount = "Checking Account",
            crAccount = "Salary"
        ),
    )

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(scheduledTransactions.size) {
            ScheduledTransactionItem(transaction = scheduledTransactions[it])
        }
    }
}

/**
 * Composable for displaying a single scheduled transaction item in a [Card].
 * This is a stateless composable that takes a [ScheduledTransaction] object and displays its details.
 * It is used within the [LazyColumn] of the [ScheduledScreen].
 *
 * @param transaction The [ScheduledTransaction] data object to display.
 */
@Composable
fun ScheduledTransactionItem(transaction: ScheduledTransaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = transaction.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = transaction.frequency, style = MaterialTheme.typography.bodySmall)
                Text(text = transaction.nextDate, style = MaterialTheme.typography.bodySmall)
                Text(text = "Dr: ${transaction.drAccount}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Cr: ${transaction.crAccount}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = transaction.amount, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
    }
}

/**
 * A preview of the [ScheduledScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun ScheduledScreenPreview() {
    Fintrack_mobile_apkTheme {
        ScheduledScreen()
    }
}
