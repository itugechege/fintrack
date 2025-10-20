package com.example.fintrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fintrack.ui.components.FinTrackMainCard
import com.example.fintrack.ui.components.QuickActionsSection

/**
 * HomeScreen.kt
 *
 * A simple, modern financial dashboard UI built with Jetpack Compose.
 * This screen shows a header, a balance card, and a mock list of recent transactions.
 * It is inspired by your Figma design and structured for clean expansion later.
 */

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { HomeTopBar() },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { FinTrackMainCard() }
            item {
                QuickActionsSection(
                    onBudgetClick = {               },
                    onPortfolioClick = {},
                    onMarketsClick = {},
                    onNewsClick = {}
                )
            }
            item { SectionTitle("Recent Transactions") }
            item {TransactionItem(mockTransactions[0])}
            items(mockTransactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

/** --- Top App Bar --- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Welcome back, ${System.getProperty("user.name")} 👋",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        fontFamily = MaterialTheme.typography.displaySmall.fontFamily
                    )
                )
                Text(
                    text = "Your accounts at a glance",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = { println("Hello world") }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

/** --- Balance Overview Card --- */
@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Balance",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$12,480.75",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "+$530.45 today",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "• Updated just now",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


/**
 * --- Quick Actions Card ---
 *
 * Displays four equally sized action buttons (2x2 grid):
 *  - Add to Portfolio
 *  - Add Transaction
 *  - Check History
 *  - Add Budget
 *
 * Each button adapts to the app's MaterialTheme.
 */
@Composable
fun QuickActionsCard(
    modifier: Modifier = Modifier,
    onAddPortfolioClick: () -> Unit = {},
    onAddTransactionClick: () -> Unit = {},
    onCheckHistoryClick: () -> Unit = {},
    onAddBudgetClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Two rows of buttons (2 per row)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickActionButton(
                    icon = Icons.Default.AddChart,
                    label = "Add to Portfolio",
                    onClick = onAddPortfolioClick
                )
                QuickActionButton(
                    icon = Icons.Default.AddCircle,
                    label = "Add Transaction",
                    onClick = onAddTransactionClick
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickActionButton(
                    icon = Icons.Default.History,
                    label = "Check History",
                    onClick = onCheckHistoryClick
                )
                QuickActionButton(
                    icon = Icons.Default.AccountBalanceWallet,
                    label = "Add Budget",
                    onClick = onAddBudgetClick
                )
            }
        }
    }
}

/**
 * Individual action button styled to match theme.
 * Equal width & height, rounded corners, icon + label vertically centered.
 */

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .aspectRatio(1.6f), // Ensures equal height/width ratio for consistent sizing
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

    }
}

/**
 * Small composable representing each Quick Action button.
 * Each button uses an icon and label inside a card.
 */
@Composable
fun ActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier

            .height(70.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}



/** --- Section Title --- */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        ),
        modifier = Modifier.padding(top = 8.dp)
    )
}

/** --- Transaction Item --- */
@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = transaction.date,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }
        Text(
            text = transaction.amount,
            color = if (transaction.amount.startsWith("-")) Color.Red else Color(0xFF4CAF50),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

/** --- Mock Data Model --- */
data class Transaction(
    val title: String,
    val date: String,
    val amount: String
)

/** --- Mock Transactions --- */
val mockTransactions = listOf(
    Transaction("Salary Deposit", "Oct 5, 2025", "+$2,500.00"),
    Transaction("Coffee - Starbucks", "Oct 4, 2025", "-$4.50"),
    Transaction("Groceries - Walmart", "Oct 3, 2025", "-$42.90"),
    Transaction("Freelance Payment", "Oct 2, 2025", "+$380.00"),
    Transaction("Netflix Subscription", "Oct 1, 2025", "-$13.99")
)


