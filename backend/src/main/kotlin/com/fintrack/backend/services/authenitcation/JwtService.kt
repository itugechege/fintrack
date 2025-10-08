package com.fintrack.backend.services.authenitcation

import com.fintrack.backend.models.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.security.Key
import io.jsonwebtoken.security.Keys
import java.util.Date

@Service
class JwtService(

) {
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    fun generateAccesToken(user: User) : String {
        return Jwts.builder()
            .setSubject(user.email)
            .claim("userId", user.id)
            .claim("username", user.username)
            .setIssuedAt(Date(System.currentTimeMillis() +15 * 60 * 1000))
            .signWith(key)
            .compact()
    }

    fun validateToken(user: User) : String {
        return Jwts.builder()
            .setSubject(user.email)
            .claim("userId", user.id)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
            .signWith(key)
            .compact()
    }

    fun validateToke(token: String) : Boolean{
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (ex: Exception){
            false
        }
    }

}