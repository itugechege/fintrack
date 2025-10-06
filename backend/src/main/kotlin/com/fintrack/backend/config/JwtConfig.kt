package com.fintrack.backend.config

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key

@Configuration
class JwtConfig {

    @Bean
    fun jwtSigningKey(): Key {
        // Symmetric HS512 key
        return Keys.secretKeyFor(SignatureAlgorithm.HS512)
    }
}