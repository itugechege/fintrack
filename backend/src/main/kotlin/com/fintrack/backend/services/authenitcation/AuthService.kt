package com.fintrack.backend.services.authenitcation

import com.fintrack.backend.models.Session
import com.fintrack.backend.models.SessionEvent
import com.fintrack.backend.models.User
import com.fintrack.backend.repo.SessionEventRepository
import com.fintrack.backend.repo.SessionRepository
import com.fintrack.backend.repo.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
//import java.time.Instant


@OptIn(ExperimentalTime::class)
class AuthService (
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val sessionEventRepository: SessionEventRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val emailService: EmailService,
    private val kafkaTemplate: StringTemplate,
    private val verificationTokeRepository: UserRepository
) {


//    // ----------------------------
//    // SIGNUP WITH EMAIL VERIFICATION
//    // ----------------------------
//    @Transactional
//    fun signup(user: User): User {
//        validateUser(user)
//        user.passwordHash = passwordEncoder.encode(user.passwordHash)
//        user.emailVerified = false
//        val savedUser = userRepository.save(user)
//
//        sendVerificationEmail(savedUser)
//        logEvent(savedUser, null, "SIGNUP", null)
//        return savedUser
//    }

    private fun validateUser(user: User) {
        require(user.email.contains("@")) { "Invalid email" }
        user.passwordHash?.length?.let { require(it >= 8) { "Password too short" } }
    }

    private fun sendVerificationEmail(user: User) {
        val token = UUID.randomUUID().toString()
        user.verificationToken = token
        user.verificationTokenExpiry =
            (Instant.fromEpochMilliseconds(260000000) ) as Instant? as kotlin.time.Instant? // 1 hour expiry
        userRepository.save(user)

        emailService.sendEmail(
            user.email,
            "Verify your account",
            "Click link to verify: https://fintrack.app/verify?token=$token"
        )
    }
//
//    @Transactional
//    fun verifyEmail(token: String): Boolean {
//        val user = userRepository.findByVerificationToken(token)
//            ?: throw IllegalArgumentException("Invalid verification token")
//        if (user.verificationTokenExpiry!! < Clock.System.now()) {
//            throw IllegalArgumentException("Token expired")
//        }
//        user.emailVerified = true
//        user.verificationToken = null
//        user.verificationTokenExpiry = null
//        userRepository.save(user)
//        logEvent(user, null, "EMAIL_VERIFIED", null)
//        return true
//    }

    // ----------------------------
    // LOGIN WITH PASSWORD OR OAUTH
    // ----------------------------
//    @Transactional
//    fun login(email: String, password: String, deviceType: String, deviceModel: String): Session {
//        val user = userRepository.findByEmail(email)
//            ?: throw IllegalArgumentException("User not found")
//
//        if (!passwordEncoder.matches(password, user.passwordHash)) {
//            throw IllegalArgumentException("Invalid credentials")
//        }
//
//        return createSession(user, deviceType, deviceModel, "LOGIN", null)
//    }

//    @Transactional
//    fun oauthLogin(user: User, provider: String, providerId: String, deviceType: String, deviceModel: String): Session {
//        val existing = userRepository.findByEmail(user.email).orElseGet { userRepository.save(user) }
//        return createSession(existing, deviceType, deviceModel, "OAUTH_LOGIN", "$provider:$providerId")
//    }

//    // ----------------------------
//    // SESSION MANAGEMENT
//    // ----------------------------
//    private fun createSession(
//        user: User,
//        deviceType: String,
//        deviceModel: String,
//        eventType: String,
//        eventData: String?
//    ): Session {
//        var session = Session(user,deviceType,deviceModel, null, isActive = true).apply {
//            this.user = user
//            this.deviceType = deviceType
//            this.deviceModel = deviceModel
//            this.sessionStart = LocalDateTime.now()
//            this.isActive = true
//        }
//        val savedSession = sessionRepository.save(session)
//        logEvent(user, savedSession, eventType, eventData)
//        return savedSession
//    }
//
//    @Transactional
//    fun logout(sessionId: Long) {
//        val session = sessionRepository.findById(sessionId)
//            .orElseThrow { IllegalArgumentException("Session not found") }
//        session.isActive = false
//        session.sessionEnd = LocalDateTime.now()
//        sessionRepository.save(session)
//        logEvent(session.user, session, "LOGOUT", null)
//    }

//    // ----------------------------
//    // PASSWORD RESET PIPELINE
//    // ----------------------------
//    @Transactional
//    fun requestPasswordReset(email: String) {
//        val user = userRepository.findByEmail(email) ?: throw IllegalArgumentException("User not found")
//        val token = UUID.randomUUID().toString()
//        user.passwordResetToken = token
//        user.passwordResetTokenExpiry = Clock.System.now().plusSeconds(1800) // 30 minutes
//        userRepository.save(user)
//
//        emailService.sendEmail(
//            email,
//            "Reset your password",
//            "Use this link to reset password: https://fintrack.app/reset?token=$token"
//        )
//        logEvent(user, null, "REQUEST_PASSWORD_RESET", null)
//    }
//
//    @Transactional
//    fun resetPassword(token: String, newPassword: String) {
//        val user = userRepository.findByPasswordResetToken(token)
//            ?: throw IllegalArgumentException("Invalid token")
//        if (user.passwordResetTokenExpiry!!.isBefore(Clock.System.now()))
//            throw IllegalArgumentException("Token expired")
//
//        user.passwordHash = passwordEncoder.encode(newPassword)
//        user.passwordResetToken = null
//        user.passwordResetTokenExpiry = null
//        userRepository.save(user)
//
//        logEvent(user, null, "RESET_PASSWORD", null)
//    }

//    // ----------------------------
//    // ACCOUNT DETAILS RESET
//    // ----------------------------
//    @Transactional
//    fun resetUsername(userId: Long, newUsername: String) {
//        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
//        user.username = newUsername
//        userRepository.save(user)
//        logEvent(user, null, "RESET_USERNAME", null)
//    }
//
//    @Transactional
//    fun resetEmail(userId: Long, newEmail: String) {
//        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
//        user.email = newEmail
//        user.emailVerified = false
//        sendVerificationEmail(user)
//        logEvent(user, null, "RESET_EMAIL", null)
//    }

    // ----------------------------
    // EVENT LOGGING
//    // ----------------------------
//    private fun logEvent(user: User, session: Session?, eventType: String, eventData: String?) {
//        val event = SessionEvent().apply {
//            this.user = user
//            this.session = session
//            this.eventType = eventType
//            this.eventData = eventData?.let { mapOf("info" to it) }
//            this.eventTime = Clock.System.now()
//        }
//        sessionEventRepository.save(event)
//    }
}