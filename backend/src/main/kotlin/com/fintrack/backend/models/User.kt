package com.fintrack.backend.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.RequiredArgsConstructor
import org.apache.el.parser.AstFalse
import org.hibernate.internal.build.AllowSysOut
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null   // Hibernate will set this later

    @Column(nullable = false, unique = true, length = 50)
    lateinit var username: String

    @Column(nullable = false, unique = true, length = 100)
    lateinit var email: String

    @Column(name = "email_verified")
    var emailVerified: Boolean = false

    @Column(name = "phone_number", length = 20)
    var phoneNumber: String? =  null

    @Column(name = "phone_verified")
     var phoneVerified: Boolean = false

    @Column(name = "password_hash", nullable = false, columnDefinition = "TEXT")
    var passwordHash: String? = null

    // Profile
    @Column(name = "first_name", nullable = false, length = 100)
    var firstName: String? = null

    @Column(name = "last_name", nullable = false, length = 100)
    var lastName: String? = null

    @Column(name = "sir_name", length = 100)
    var sirName: String? = null

    @Column(name = "date_of_birth")
    var dateOfBirth: LocalDate? = null

    var gender: String? = null
    var country: String? = null
    var city: String? = null
    var occupation: String? = null

    @Column(name = "income_bracket")
    var incomeBracket: String? = null

    @Column(name = "preferred_currency", length = 3)
    var preferredCurrency: String = "usd"

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()




}