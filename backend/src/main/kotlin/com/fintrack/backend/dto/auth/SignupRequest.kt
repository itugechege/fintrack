package com.fintrack.backend.dto.auth

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)