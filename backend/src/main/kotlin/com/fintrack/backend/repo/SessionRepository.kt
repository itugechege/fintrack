package com.fintrack.backend.repo

import com.fintrack.backend.models.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SessionRepository : JpaRepository<Session, Long> {
}