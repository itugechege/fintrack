package com.fintrack.backend.repo

import com.fintrack.backend.models.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ReportRepository : JpaRepository<Report, Int> {
}