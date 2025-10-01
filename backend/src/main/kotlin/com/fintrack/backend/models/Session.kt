package com.fintrack.backend.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
@Table(name = "sessions")
class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @Column(name = "session_start")
    var sessionStart: LocalDateTime = LocalDateTime.now()

    @Column(name = "session_end")
    var sessionEnd: LocalDateTime? = null

    @Column(name = "is_active")
    var isActive: Boolean = true

    // Device info
    @Column(name = "device_type")
    var deviceType: String? = null

    @Column(name = "device_model")
    var deviceModel: String? = null

    @Column(name = "os_version")
    var osVersion: String? = null

    @Column(name = "app_version")
    var appVersion: String? = null

    // Network & location
    @Column(name = "ip_address")
    var ipAddress: String? = null

    @Column(name = "network_type")
    var networkType: String? = null

    var location: String? = null

    // Metrics
    @Column(name = "actions_count")
    var actionsCount: Int = 0

    @Column(name = "transactions_count")
    var transactionsCount: Int = 0

    @Column(name = "avg_screen_time")
    var avgScreenTime: BigDecimal? = null

    @Column(name = "crash_reports", columnDefinition = "jsonb")
    var crashReports: String? = null
}