package com.example.fintrack_mobile_apk.budget

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.FintrackViewModel
import com.example.fintrack_mobile_apk.data.Transaction
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "BudgetScreen"

/**
 * Composable function for the Budget screen.
 * This screen displays the user's budget and spending progress for various categories.
 * It is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.BUDGET] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param viewModel The [FintrackViewModel] instance for the app.
 */
@Composable
fun BudgetScreen(modifier: Modifier = Modifier, viewModel: FintrackViewModel) {
    Log.d(TAG, "BudgetScreen: Composing")
    val transactions by viewModel.allTransactions.collectAsState()

    // For now, we'll just display a list of all transactions.
    // In the future, we can group them by category and calculate the budget progress.

    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Text(text = "Monthly Budget", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(bottom = 16.dp))
        }
        items(transactions.size) {
            BudgetItemItem(transaction = transactions[it])
        }
    }
}

/**
 * Composable for displaying a single budget item in a [Card].
 * This is a stateless composable that takes a [Transaction] object and displays its details,
 * including a [LinearProgressIndicator] to visualize budget usage.
 * It is used within the [LazyColumn] of the [BudgetScreen].
 *
 * @param transaction The [Transaction] data object to display.
 */
@Composable
fun BudgetItemItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = transaction.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "$${transaction.amount}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.End
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier.fillMaxWidth(),
                color = if (transaction.amount < 0) Color(0xFFD97706) else Color(0xFF059669)
            )
        }
    }
}

/**
 * A preview of the [BudgetScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun BudgetScreenPreview() {
    Fintrack_mobile_apkTheme {
        BudgetItemItem(transaction = Transaction(name = "Groceries", date = System.currentTimeMillis(), category = "Groceries", amount = -450.30))
    }
}
