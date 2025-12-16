package com.example.fintrack_mobile_apk.transactions

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.FintrackViewModel
import com.example.fintrack_mobile_apk.data.Transaction
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG = "TransactionsScreen"

/**
 * Composable function for the Transactions screen.
 * This screen displays a list of the user's financial transactions using a [LazyColumn].
 * It is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.TRANSACTIONS] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param viewModel The [FintrackViewModel] instance for the app.
 */
@Composable
fun TransactionsScreen(modifier: Modifier = Modifier, viewModel: FintrackViewModel) {
    Log.d(TAG, "TransactionsScreen: Composing")
    val transactions by viewModel.allTransactions.collectAsState()

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(transactions.size) {
            TransactionItem(transaction = transactions[it])
        }
    }
}

/**
 * Composable for displaying a single transaction item in a [Card].
 * This is a stateless composable that takes a [Transaction] object and displays its details.
 * It is used within the [LazyColumn] of the [TransactionsScreen].
 *
 * @param transaction The [Transaction] data object to display.
 */
@Composable
fun TransactionItem(transaction: Transaction) {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
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
                Text(text = "${sdf.format(transaction.date)} • ${transaction.category}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "$${transaction.amount}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

/**
 * A preview of the [TransactionsScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun TransactionsScreenPreview() {
    Fintrack_mobile_apkTheme {
        TransactionItem(transaction = Transaction(name = "Monthly Salary", date = System.currentTimeMillis(), category = "Salary", amount = 6500.00))
    }
}
