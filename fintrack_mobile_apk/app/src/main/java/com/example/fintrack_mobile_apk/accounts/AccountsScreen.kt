package com.example.fintrack_mobile_apk.accounts

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.fintrack_mobile_apk.FintrackViewModel
import com.example.fintrack_mobile_apk.data.Account
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "AccountsScreen"

/**
 * Composable function for the Accounts screen.
 * This screen displays a list of the user's financial accounts using a [LazyColumn].
 * It is called from [com.example.fintrack_mobile_apk.FintrackMobileApkApp] when the
 * [com.example.fintrack_mobile_apk.AppDestinations.ACCOUNTS] is selected.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param viewModel The [FintrackViewModel] instance for the app.
 */
@Composable
fun AccountsScreen(modifier: Modifier = Modifier, viewModel: FintrackViewModel) {
    Log.d(TAG, "AccountsScreen: Composing")
    val accounts by viewModel.allAccounts.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "New Account")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (showDialog) {
                NewAccountDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        viewModel.insertAccount(it)
                        showDialog = false
                    }
                )
            }

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(accounts.size) {
                    AccountItem(account = accounts[it])
                }
            }
        }
    }
}

/**
 * Composable for displaying a single account item in a [Card].
 * This is a stateless composable that takes an [Account] object and displays its details.
 * It is used within the [LazyColumn] of the [AccountsScreen].
 *
 * @param account The [Account] data object to display.
 */
@Composable
fun AccountItem(account: Account) {
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
                Text(text = account.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = account.type, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "$${account.balance}", style = MaterialTheme.typography.bodyLarge, color = if (account.balance < 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun NewAccountDialog(onDismiss: () -> Unit, onConfirm: (Account) -> Unit) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "New Account", style = MaterialTheme.typography.titleLarge)
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Type") })
                OutlinedTextField(value = balance, onValueChange = { balance = it }, label = { Text("Balance") })
                Row {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        val account = Account(
                            name = name,
                            type = type,
                            balance = balance.toDouble(),
                            balanceColor = 0xFF000000
                        )
                        onConfirm(account)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

/**
 * A preview of the [AccountsScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun AccountsScreenPreview() {
    Fintrack_mobile_apkTheme {
        AccountItem(account = Account(name = "Checking Account", type = "ASSET", balance = 5420.50, balanceColor = 0xFF000000))
    }
}
