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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "BudgetScreen"

/**
 * Data class representing a single budget item.
 * This structure holds the information for each budget category displayed on the [BudgetScreen].
 *
 * @param name The name of the budget category (e.g., "Groceries").
 * @param spent The amount of money spent in this category so far, formatted as a string.
 * @param total The total budgeted amount for this category, formatted as a string.
 * @param progress A float value between 0.0 and 1.0 representing the budget usage, used for the [LinearProgressIndicator].
 * @param progressColor The color of the progress indicator, used to visually represent the budget status (e.g., green for under budget, red for over budget).
 */
data class BudgetItem(
    val name: String,
    val spent: String,
    val total: String,
    val progress: Float,
    val progressColor: Color
)

/**
 * Composable function for the Budget screen.
 * This screen displays the user's budget and spending progress for various categories.
 * It is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.BUDGET] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 */
@Composable
fun BudgetScreen(modifier: Modifier = Modifier) {
    Log.d(TAG, "BudgetScreen: Composing")
    // Placeholder data for the list of budget items. In a real app, this would be fetched from a ViewModel or repository.
    val budgetItems = listOf(
        BudgetItem("Groceries", "$450.30", "$600.00", 0.75f, Color(0xFF059669)),
        BudgetItem("Rent", "$1,500.00", "$1,500.00", 1.0f, Color(0xFFD97706)),
        BudgetItem("Utilities", "$180.50", "$200.00", 0.9f, Color(0xFF059669)),
        BudgetItem("Transportation", "$220.00", "$300.00", 0.73f, Color(0xFF059669)),
        BudgetItem("Entertainment", "$150.00", "$200.00", 0.75f, Color(0xFF059669)),
    )

    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Text(text = "Monthly Budget", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(bottom = 16.dp))
        }
        items(budgetItems.size) {
            BudgetItemItem(budgetItem = budgetItems[it])
        }
    }
}

/**
 * Composable for displaying a single budget item in a [Card].
 * This is a stateless composable that takes a [BudgetItem] object and displays its details,
 * including a [LinearProgressIndicator] to visualize budget usage.
 * It is used within the [LazyColumn] of the [BudgetScreen].
 *
 * @param budgetItem The [BudgetItem] data object to display.
 */
@Composable
fun BudgetItemItem(budgetItem: BudgetItem) {
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
                Text(text = budgetItem.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${budgetItem.spent} / ${budgetItem.total}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.End
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            LinearProgressIndicator(
                progress = { budgetItem.progress },
                modifier = Modifier.fillMaxWidth(),
                color = budgetItem.progressColor
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
        BudgetScreen()
    }
}
