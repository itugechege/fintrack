package com.fintrack.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "market_assets")
class MarketAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    lateinit var symbol: String
    lateinit var name: String
    @Enumerated(EnumType.STRING)
    lateinit var type: AssetType
    lateinit var currency: String
}