package com.example.fintrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun BudgetPlannerScreen() {
    val purple = Color(0xFF7E57C2)
    val lightPurple = Color(0xFFF3E5F5)

    var selectedTab by remember { mutableStateOf("Categories") }
    var totalBudget by remember { mutableStateOf(8000.0) }
    var totalSpent by remember { mutableStateOf(7875.0) }
    val progress = (totalSpent / totalBudget).toFloat().coerceIn(0f, 1f)

    val remaining = totalBudget - totalSpent

    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    val categories = remember {
        mutableStateListOf(
            BudgetCategory("Housing", "High", 2750.0, 2800.0, Color(0xFFFFD54F)),
            BudgetCategory("Food", "Over", 920.0, 800.0, Color(0xFFFF8A80)),
            BudgetCategory("Transport", "Good", 485.0, 600.0, Color(0xFFA5D6A7)),
            BudgetCategory("Healthcare", "Good", 320.0, 400.0, Color(0xFFA5D6A7)),
            BudgetCategory("Entertainment", "Good", 380.0, 500.0, Color(0xFFA5D6A7))
        )
    }

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FloatingActionButton(
                    onClick = { dialogType = "income"; showDialog = true },
                    containerColor = Color(0xFF4CAF50)
                ) {
                    Icon(Icons.Default.AttachMoney, contentDescription = "Add Income", tint = Color.White)
                }
                FloatingActionButton(
                    onClick = { dialogType = "expense"; showDialog = true },
                    containerColor = Color(0xFFF44336)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Add Expense", tint = Color.White)
                }
                FloatingActionButton(
                    onClick = { dialogType = "savings"; showDialog = true },
                    containerColor = purple
                ) {
                    Icon(Icons.Default.Savings, contentDescription = "Add Savings", tint = Color.White)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Card
            HeaderCard(
                totalBudget = totalBudget,
                totalSpent = totalSpent,
                remaining = remaining,
                progress = progress,
                purple = purple,
                lightPurple = lightPurple
            )

            // Tabs
            BudgetTabs(
                selectedTab = selectedTab,
                onSelect = { selectedTab = it },
                purple = purple,
                lightPurple = lightPurple
            )

            // Tab Content
            when (selectedTab) {
                "Categories" -> {
                    categories.forEach {
                        CategoryCard(category = it, accentColor = purple)
                    }
                }

                "AI Insights" -> {
                    InsightCard(
                        title = "Smart Suggestions",
                        content = "Consider reducing dining out by 10% to stay under your food budget."
                    )
                    InsightCard(
                        title = "Savings Tip",
                        content = "Automate your monthly savings right after payday to ensure consistency."
                    )
                }

                "Analysis" -> {
                    InsightCard(
                        title = "Spending Breakdown",
                        content = "Housing and Food account for 60% of your spending this month."
                    )
                    InsightCard(
                        title = "Budget Health",
                        content = "You are on track to end the month with a 2% underspend."
                    )
                }
            }
        }

        // Dialog for input
        if (showDialog) {
            InputDialog(
                type = dialogType,
                onDismiss = { showDialog = false },
                onSubmit = { amount ->
                    when (dialogType) {
                        "income" -> totalBudget += amount
                        "expense" -> totalSpent += amount
                        "savings" -> totalBudget += 0 // placeholder logic
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun HeaderCard(
    totalBudget: Double,
    totalSpent: Double,
    remaining: Double,
    progress: Float,
    purple: Color,
    lightPurple: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = purple),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Budget Planner", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Intelligent budget management", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = lightPurple),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Monthly Budget", color = purple, fontSize = 14.sp)
                        Text(
                            "$${String.format("%,.0f", totalBudget)}",
                            color = purple,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = progress,
                        color = purple,
                        trackColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Spent\n$${String.format("%,.0f", totalSpent)}", color = purple, fontSize = 13.sp)
                        Text("Remaining\n$${String.format("%,.0f", remaining)}", color = purple, fontSize = 13.sp)
                        Text("Progress\n${(progress * 100).toInt()}%", color = purple, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun BudgetTabs(selectedTab: String, onSelect: (String) -> Unit, purple: Color, lightPurple: Color) {
    val tabs = listOf("Categories", "AI Insights", "Analysis")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(50))
            .background(lightPurple),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach {
            Text(
                text = it,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (selectedTab == it) Color.White else Color.Transparent)
                    .clickable { onSelect(it) }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                color = if (selectedTab == it) purple else Color.Gray,
                fontWeight = if (selectedTab == it) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

data class BudgetCategory(
    val name: String,
    val status: String,
    val spent: Double,
    val limit: Double,
    val statusColor: Color
)

@Composable
fun CategoryCard(category: BudgetCategory, accentColor: Color) {
    val progress = (category.spent / category.limit).toFloat().coerceIn(0f, 1f)
    val overBudget = category.spent > category.limit
    val remaining = category.limit - category.spent
    val percentUsed = (progress * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(category.name, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(category.statusColor.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(category.status, color = category.statusColor, fontSize = 12.sp)
                    }
                }
                Text(
                    "$${category.spent.toInt()} / $${category.limit.toInt()}",
                    fontWeight = FontWeight.Bold
                )
            }

            Text("Needs", color = Color.Gray, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = progress,
                color = accentColor,
                trackColor = accentColor.copy(alpha = 0.2f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("$percentUsed% used", color = Color.Gray, fontSize = 13.sp)
                Text(
                    if (overBudget) "$${-remaining.toInt()} over" else "$${remaining.toInt()} left",
                    color = if (overBudget) Color.Red else Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun InsightCard(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(content, color = Color.Gray)
        }
    }
}

@Composable
fun InputDialog(type: String, onDismiss: () -> Unit, onSubmit: (Double) -> Unit) {
    var amountText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add ${type.replaceFirstChar { it.uppercase() }}") },
        text = {
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Enter amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            TextButton(onClick = {
                val amount = amountText.toDoubleOrNull()
                if (amount != null) onSubmit(amount)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
