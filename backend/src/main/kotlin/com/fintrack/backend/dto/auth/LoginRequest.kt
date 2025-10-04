package com.fintrack.backend.dto.auth

data class LoginRequest(
    val email: String,
    val password: String,
    val deviceType: String?,
    val deviceModel: String?
)
