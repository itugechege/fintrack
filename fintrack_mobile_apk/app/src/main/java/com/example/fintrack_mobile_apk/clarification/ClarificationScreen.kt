package com.example.fintrack_mobile_apk.clarification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fintrack_mobile_apk.FintrackViewModel
import com.example.fintrack_mobile_apk.data.Transaction

@Composable
fun ClarificationScreen(viewModel: FintrackViewModel) {
    val transactions by viewModel.transactionsForClarification.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (transactions.isEmpty()) {
            Text(text = "No transactions need clarification.")
        } else {
            LazyColumn {
                items(transactions.size) {
                    TransactionClarificationItem(transaction = transactions[it], onClarify = { category ->
                        viewModel.clarifyTransaction(transactions[it], category)
                    })
                }
            }
        }
    }
}

@Composable
fun TransactionClarificationItem(transaction: Transaction, onClarify: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = transaction.name)
            Text(text = "$${transaction.amount}")
            Row {
                Button(onClick = { onClarify("Groceries") }) {
                    Text(text = "Groceries")
                }
                Button(onClick = { onClarify("Transport") }) {
                    Text(text = "Transport")
                }
                // Add more category buttons as needed
            }
        }
    }
}
