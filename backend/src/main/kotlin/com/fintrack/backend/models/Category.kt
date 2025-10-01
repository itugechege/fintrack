package com.fintrack.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "categories")
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    lateinit var name: String
    @Enumerated(EnumType.STRING)
    lateinit var type: TransactionType
    var totalTransactions: Int = 0
    var totalAmount: BigDecimal = BigDecimal.ZERO
}