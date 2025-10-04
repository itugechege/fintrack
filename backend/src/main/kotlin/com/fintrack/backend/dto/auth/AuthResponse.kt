package com.fintrack.backend.dto.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val email: String,
    val username: String
)
