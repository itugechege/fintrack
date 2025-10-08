package com.fintrack.backend.repo

import com.fintrack.backend.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional


@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): java.util.Optional<com.fintrack.backend.models.User?>
    fun findByVerificationToken(token: String) : User?
    fun findByPasswordResetToken(token: String): User?

}