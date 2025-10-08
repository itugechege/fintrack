package com.fintrack.backend.repo

import com.fintrack.backend.models.SessionFactor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SessionFactorRepository : JpaRepository<SessionFactor, Long> {

}