package com.fintrack.backend.services

import com.fintrack.backend.models.User
import com.fintrack.backend.repo.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getAllUsers(id: Long): Optional<User> = userRepository.findById(id)

    fun getUserByEmail(email: String) : Optional<User> = userRepository.findByEmail(email) as Optional<User>


    @Transactional
    @OptIn(kotlin.time.ExperimentalTime::class)
    fun updateUser(id: Long, update: User): User {
        val existing = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found") }

        update.email?.let { existing.email = it }
        update.phoneNumber?.let { existing.phoneNumber = it }
        update.preferredCurrency?.let { existing.preferredCurrency = it }
        existing.updatedAt = LocalDateTime.now()

        return userRepository.save(existing)
    }

    @Transactional
    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}