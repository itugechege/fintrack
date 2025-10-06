package com.fintrack.backend.repo

import com.fintrack.backend.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByAccountId(accountId: Long): List<Transaction>
    fun findByAccountUserId(userId: Long): List<Transaction>
}