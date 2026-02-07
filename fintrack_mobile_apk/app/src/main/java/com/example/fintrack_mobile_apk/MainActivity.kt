package com.example.fintrack_mobile_apk

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.fintrack_mobile_apk.accounts.AccountsScreen
import com.example.fintrack_mobile_apk.budget.BudgetScreen
import com.example.fintrack_mobile_apk.dashboard.DashboardScreen
import com.example.fintrack_mobile_apk.reports.ReportsScreen
import com.example.fintrack_mobile_apk.scheduled.ScheduledScreen
import com.example.fintrack_mobile_apk.setup.SetupScreen
import com.example.fintrack_mobile_apk.transactions.TransactionsScreen
import com.example.fintrack_mobile_apk.ui.theme.Fintrack_mobile_apkTheme

private const val TAG = "MainActivity"

/**
 * The main activity of the Fintrack app.
 * This activity hosts the entire application and serves as the entry point.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: FintrackViewModel by viewModels {
        FintrackViewModelFactory(
            (application as FintrackApplication).repository,
            (application as FintrackApplication).settingsRepository,
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Log.d(TAG, "onCreate: Activity created")
        enableEdgeToEdge()
        setContent {
            val setupComplete by viewModel.setupComplete.collectAsState()
            Fintrack_mobile_apkTheme {
                if (setupComplete) {
                    FintrackMobileApkApp(viewModel = viewModel)
                } else {
                    SetupScreen(onSetupComplete = { viewModel.completeSetup() })
                }
            }
        }
    }
}

/**
 * The main composable for the Fintrack app.
 * This composable sets up the navigation scaffold and routes to the different screens.
 * It is the root composable of the application.
 *
 * @param viewModel The [FintrackViewModel] instance for the app.
 */
@Composable
fun FintrackMobileApkApp(viewModel: FintrackViewModel) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.DASHBOARD) }

    Log.d(TAG, "FintrackMobileApkApp: Current destination: $currentDestination")

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) },
                    selected = destination == currentDestination,
                    onClick = {
                        Log.d(TAG, "FintrackMobileApkApp: Navigating to ${destination.label}")
                        currentDestination = destination
                    }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (currentDestination) {
                AppDestinations.DASHBOARD -> DashboardScreen(modifier = modifier, viewModel = viewModel)
                AppDestinations.ACCOUNTS -> AccountsScreen(modifier = modifier, viewModel = viewModel)
                AppDestinations.TRANSACTIONS -> TransactionsScreen(modifier = modifier, viewModel = viewModel)
                AppDestinations.SCHEDULED -> ScheduledScreen(modifier = modifier, viewModel = viewModel)
                AppDestinations.BUDGET -> BudgetScreen(modifier = modifier, viewModel = viewModel)
                AppDestinations.REPORTS -> ReportsScreen(modifier = modifier, viewModel = viewModel)
            }
        }
    }
}

/**
 * Enum class representing the primary destinations in the app.
 * Each destination has a label for display and an icon.
 * This enum is used to drive the navigation in [FintrackMobileApkApp].
 */
enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    /**
     * The main dashboard screen, showing an overview of finances.
     * This is the default screen when the app is opened.
     * It is associated with the [DashboardScreen] composable.
     */
    DASHBOARD("Dashboard", Icons.Filled.Dashboard),
    /**
     * Screen for managing accounts.
     * This screen is associated with the [AccountsScreen] composable.
     */
    ACCOUNTS("Accounts", Icons.Filled.AccountBalance),
    /**
     * Screen for viewing and managing transactions.
     * This screen is associated with the [TransactionsScreen] composable.
     */
    TRANSACTIONS("Transactions", Icons.Filled.SyncAlt),
    /**
     * Screen for managing scheduled/recurring transactions.
     * This screen is associated with the [ScheduledScreen] composable.
     */
    SCHEDULED("Scheduled", Icons.Filled.Schedule),
    /**
     * Screen for managing budgets.
     * This screen is associated with the [BudgetScreen] composable.
     */
    BUDGET("Budget", Icons.Filled.PieChart),
    /**
     * Screen for viewing financial reports.
     * This screen is associated with the [ReportsScreen] composable.
     */
    REPORTS("Reports", Icons.Filled.Assessment),
}

/**
 * A simple placeholder screen for unimplemented features.
 * This is used to avoid crashing the app when a destination is not yet implemented.
 *
 * @param text The text to display on the screen.
 * @param modifier The modifier to apply to the screen.
 */
@Composable
fun PlaceholderScreen(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
    )
}

/**
 * A preview for the [PlaceholderScreen].
 * This is used for development and testing purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Fintrack_mobile_apkTheme {
        PlaceholderScreen("This is a preview")
    }
}
