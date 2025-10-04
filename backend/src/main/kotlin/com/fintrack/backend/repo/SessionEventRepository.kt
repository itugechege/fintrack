package com.fintrack.backend.repo

import com.fintrack.backend.models.SessionEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SessionEventRepository : JpaRepository<SessionEvent, Long> {
}