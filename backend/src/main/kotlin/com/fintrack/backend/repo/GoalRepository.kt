package com.fintrack.backend.repo

import com.fintrack.backend.models.Goal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface GoalRepository : JpaRepository<Goal, Long> {
}