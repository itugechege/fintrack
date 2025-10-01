package com.fintrack.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "goals")
class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User

    lateinit var name: String
    lateinit var targetAmount: BigDecimal
    var currentAmount: BigDecimal = BigDecimal.ZERO
    var progressPercentage: BigDecimal = BigDecimal.ZERO
    var numContributions: Int = 0
    var createdAt: LocalDateTime = LocalDateTime.now()
    var dueDate: LocalDateTime? = null
}