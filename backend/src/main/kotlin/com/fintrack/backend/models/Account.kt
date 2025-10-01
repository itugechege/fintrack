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
@Table(name = "accounts")
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User
    lateinit var name: String
    @Enumerated(EnumType.STRING)
    lateinit var type: AccountType
    lateinit var currency: String
    var provider: String? = null
    var balance: BigDecimal = BigDecimal.ZERO
    var availableBalance: BigDecimal = BigDecimal.ZERO
    var totalInflow: BigDecimal = BigDecimal.ZERO
    var totalOutflow: BigDecimal = BigDecimal.ZERO
    var numTransactions: Int = 0
    var accountCreatedAt: LocalDateTime = LocalDateTime.now()
    var accountUpdatedAt: LocalDateTime = LocalDateTime.now()
    var closedAt: LocalDateTime? = null
    var lastSynced: LocalDateTime? = null
}