package com.fintrack.backend.repo

import com.fintrack.backend.models.UserPortfolio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserPortfolioRepository : JpaRepository<UserPortfolio, Long> {
}