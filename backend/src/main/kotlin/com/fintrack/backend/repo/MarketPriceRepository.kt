package com.fintrack.backend.repo

import com.fintrack.backend.models.MarketPrice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface MarketPriceRepository : JpaRepository<MarketPrice, Long> {
}