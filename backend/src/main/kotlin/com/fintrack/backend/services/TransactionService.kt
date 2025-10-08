package com.fintrack.backend.services

import com.fintrack.backend.models.Account
import com.fintrack.backend.models.Transaction
import com.fintrack.backend.models.TransactionType
import com.fintrack.backend.repo.AccountRepository
import com.fintrack.backend.repo.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service

class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    fun getAllTransactions(): List<Transaction> = transactionRepository.findAll()

    fun getTransactionsByAccount(accountId: Long): List<Transaction> =
        transactionRepository.findByAccountId(accountId)

    fun getTransactionsByUser(userId: Long): List<Transaction> =
        transactionRepository.findByAccountUserId(userId)

    fun getTransactionById(id: Long): Transaction =
        transactionRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Transaction not found") }

    @Transactional
    fun createTransaction(accountId: Long, tx: Transaction): Transaction {
        val account: Account = accountRepository.findById(accountId)
            .orElseThrow { IllegalArgumentException("Account not found") }

        // Business rules: check balance if EXPENSE
        if (tx.type == TransactionType.EXPENSE && account.balance < tx.amount) {
            throw IllegalStateException("Insufficient funds")
        }

        // Update account balances
        when (tx.type) {
            TransactionType.EXPENSE -> {
                account.balance = account.balance.subtract(tx.amount)
                account.totalOutflow = account.totalOutflow.add(tx.amount)
            }
            TransactionType.INCOME -> {
                account.balance = account.balance.add(tx.amount)
                account.totalInflow = account.totalInflow.add(tx.amount)
            }
            else -> { /* TRANSFER logic can be added here if needed */ }
        }

        account.numTransactions += 1
        accountRepository.save(account)

        tx.account = account
        return transactionRepository.save(tx)
    }

    @Transactional
    fun updateTransaction(id: Long, update: Transaction): Transaction {
        val existing = transactionRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Transaction not found") }

        update.amount?.let { existing.amount = it }
        update.category?.let { existing.category = it }
        update.description?.let { existing.description = it }
        update.location?.let { existing.location = it }
        update.merchant?.let { existing.merchant = it }

        return transactionRepository.save(existing)
    }

    @Transactional
    fun deleteTransaction(id: Long): Boolean {
        return if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}