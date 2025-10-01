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
@Table(name = "user_portfolio")
class UserPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?= null

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User

    @ManyToOne @JoinColumn(name = "asset_id")
    lateinit var asset: MarketAsset
    var quantity: BigDecimal = BigDecimal.ZERO
    var avgPrice: BigDecimal = BigDecimal.ZERO
    var currentPrice: BigDecimal = BigDecimal.ZERO
    var lastUpdated: LocalDateTime = LocalDateTime.now()
}