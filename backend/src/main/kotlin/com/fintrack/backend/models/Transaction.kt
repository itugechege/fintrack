package com.fintrack.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "account_id")
    lateinit var account: Account

    @Enumerated(EnumType.STRING)
    lateinit var type: TransactionType
    var category: String? = null
    lateinit var amount: BigDecimal
    var location: String? = null
    var merchant: String? = null
    var description: String? = null
    var paymentMethod: String? = null
    var recurring: Boolean = false
    var tag: String? = null
    var transactionDate: LocalDateTime = LocalDateTime.now()
}