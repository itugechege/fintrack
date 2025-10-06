package com.fintrack.backend.repo

import com.fintrack.backend.models.MarketAsset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface MarketAssetRepository : JpaRepository<MarketAsset, Long> {
}