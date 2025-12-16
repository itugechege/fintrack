package com.example.fintrack_mobile_apk.reports

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "ReportsScreen"

/**
 * Composable function for the Reports screen.
 * This screen displays financial reports, including a summary and charts.
 * It is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.REPORTS] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 */
@Composable
fun ReportsScreen(modifier: Modifier = Modifier) {
    Log.d(TAG, "ReportsScreen: Composing")
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Reports", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Summary
        SummaryReport()

        Spacer(modifier = Modifier.height(16.dp))

        // Charts
        ChartPlaceholder(title = "Income by Category")
        ChartPlaceholder(title = "Expenses by Category")
    }
}

/**
 * A placeholder composable for the summary report card.
 * This is used on the [ReportsScreen] to display a summary of income, expenses, and net income.
 */
@Composable
fun SummaryReport() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Total Income: $8,500.00")
            Text(text = "Total Expenses: $2,500.80")
            Text(text = "Net Income: $5,999.20")
        }
    }
}

/**
 * A placeholder composable for a chart.
 * This is used on the [ReportsScreen] to indicate where charts will be displayed in the future.
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
 * A preview of the [ReportsScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    Fintrack_mobile_apkTheme {
        ReportsScreen()
    }
}
