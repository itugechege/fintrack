package com.fintrack.backend.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "reports")
class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = User()

    @Enumerated(EnumType.STRING)
    lateinit var type: ReportType

    @Column(columnDefinition = "jsonb")
    lateinit var dataJson: String

    val generatedAt: LocalDateTime = LocalDateTime.now()
}