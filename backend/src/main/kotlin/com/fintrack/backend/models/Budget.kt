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
@Table(name = "budgets")
class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User
    lateinit var name: String
    var amount: BigDecimal? = null
    @Enumerated(EnumType.STRING)
    var period: BudgetPeriod = BudgetPeriod.MONTHLY
    var category: String? = null
    var startDate: LocalDateTime = LocalDateTime.now()
    var endDate: LocalDateTime? = null
    var spendAmount: BigDecimal = BigDecimal.ZERO
    var remainingBalance: BigDecimal = BigDecimal.ZERO
    var budgetStatus: Boolean = true
}