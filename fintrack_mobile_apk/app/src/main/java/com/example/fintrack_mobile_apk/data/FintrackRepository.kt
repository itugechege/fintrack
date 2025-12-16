package com.example.fintrack_mobile_apk.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository class that serves as a single source of truth for all app data.
 * It abstracts the data sources (Room database, remote API, etc.) from the rest of the application.
 *
 * @param accountDao The DAO for the `accounts` table.
 * @param transactionDao The DAO for the `transactions` table.
 */
class FintrackRepository(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) {

    /**
     * Retrieves all accounts from the database.
     */
    val allAccounts: Flow<List<Account>> = accountDao.getAllAccounts()

    /**
     * Retrieves all transactions from the database.
     */
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    /**
     * Inserts a new account into the database.
     */
    suspend fun insertAccount(account: Account) {
        accountDao.insert(account)
    }

    /**
     * Inserts a new transaction into the database.
     */
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
}
