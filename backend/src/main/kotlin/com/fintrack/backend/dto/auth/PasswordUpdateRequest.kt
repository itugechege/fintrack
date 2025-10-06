package com.fintrack.backend.dto.auth

data class PasswordUpdateRequest(
    val token: String,
    val newPassword: String
)
