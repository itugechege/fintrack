package com.example.fintrack_mobile_apk.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.AppDestinations
import com.example.fintrack_mobile_apk.FintrackViewModel
import com.example.fintrack_mobile_apk.sms.SmsPermissionRequester
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "DashboardScreen"

/**
 * Composable function for the main dashboard screen.
 * This screen provides an overview of the user's finances, including summary cards for
 * net worth, assets, income, and expenses. It also includes placeholders for charts and
 * an account summary, which will be implemented in the future.
 *
 * This composable is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.DASHBOARD] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param viewModel The [FintrackViewModel] instance for the app.
 */
@Composable
fun DashboardScreen(modifier: Modifier = Modifier, viewModel: FintrackViewModel, onNavigate: (AppDestinations) -> Unit) {
    Log.d(TAG, "DashboardScreen: Composing")
    val accounts by viewModel.allAccounts.collectAsState()
    val transactions by viewModel.allTransactions.collectAsState()
    val transactionsForClarification by viewModel.transactionsForClarification.collectAsState()

    val netWorth = accounts.sumOf { it.balance }
    val assets = accounts.filter { it.type == "ASSET" }.sumOf { it.balance }
    val income = transactions.filter { it.amount > 0 }.sumOf { it.amount }
    val expenses = transactions.filter { it.amount < 0 }.sumOf { it.amount }

    var needsSmsPermission by remember { mutableStateOf(false) }

    if (needsSmsPermission) {
        SmsPermissionRequester(
            onPermissionGranted = {
                needsSmsPermission = false
                viewModel.importSmsTransactions()
            },
            onPermissionDenied = {
                needsSmsPermission = false
                // Show a message to the user explaining why the permission is needed
            }
        )
    }

    Column(modifier = modifier.padding(16.dp)) {
        if (transactionsForClarification.isNotEmpty()) {
            ClarificationCard(count = transactionsForClarification.size) {
                onNavigate(AppDestinations.CLARIFICATION)
            }
        }

        // Summary cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryCard(title = "Net Worth", amount = "$${String.format("%.2f", netWorth)}")
            SummaryCard(title = "Assets", amount = "$${String.format("%.2f", assets)}")
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryCard(title = "Income (Month)", amount = "$${String.format("%.2f", income)}", amountColor = Color(0xFF059669))
            SummaryCard(title = "Expenses (Month)", amount = "$${String.format("%.2f", expenses)}", amountColor = Color(0xFFB45309))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { needsSmsPermission = true }) {
            Text(text = "Import SMS Transactions")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Charts
        ChartPlaceholder(title = "Income vs Expenses (6 Months)")
        ChartPlaceholder(title = "Net Income Trend")
        ChartPlaceholder(title = "Top Expense Categories")

        Spacer(modifier = Modifier.height(16.dp))

        // Account Summary
        AccountSummary()
    }
}

/**
 * A reusable card composable that displays a title and an amount.
 * This is used on the [DashboardScreen] to show key financial figures.
 *
 * @param title The title to display on the card.
 * @param amount The amount to display on the card.
 * @param amountColor The color of the amount text. This is used to indicate positive or negative values.
 */
@Composable
fun SummaryCard(title: String, amount: String, amountColor: Color = Color.Unspecified) {
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
            Text(text = amount, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = amountColor)
        }
    }
}

/**
 * A card that prompts the user to clarify transactions.
 *
 * @param count The number of transactions that need clarification.
 * @param onClarifyClick A callback to be invoked when the user clicks the clarify button.
 */
@Composable
fun ClarificationCard(count: Int, onClarifyClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "You have $count transactions to clarify", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onClarifyClick) {
                Text(text = "Clarify")
            }
        }
    }
}

/**
 * A placeholder composable for a chart.
 * This is used on the [DashboardScreen] to indicate where charts will be displayed in the future.
 *
 * @param title The title of the chart.
 */
@Composable
fun ChartPlaceholder(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Chart will be displayed here.")
        }
    }
}

/**
 * A placeholder composable for the account summary.
 * This is used on the [DashboardScreen] to indicate where the account summary will be displayed in the future.
 */
@Composable
fun AccountSummary() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Account Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Account summary will be displayed here.")
        }
    }
}


/**
 * A preview of the [DashboardScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    Fintrack_mobile_apkTheme {
        Text("Dashboard Screen Preview")
    }
}
