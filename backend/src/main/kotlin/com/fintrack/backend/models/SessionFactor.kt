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
@Table(name = "session_factors")
class SessionFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var factorId: Long? = null

    @ManyToOne
    @JoinColumn(name = "session_id")
    var session: Session? = null

    @ManyToOne @JoinColumn(name = "user_id")
    lateinit var user: User
    lateinit var factorType: String
    var factorValue: String? = null
    var factorScore: BigDecimal? = null
    var recordedAt: LocalDateTime = LocalDateTime.now()
}