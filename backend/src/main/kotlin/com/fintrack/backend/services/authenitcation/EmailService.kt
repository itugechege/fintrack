package com.fintrack.backend.services.authenitcation

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService(
    private val mailSender: JavaMailSender
) {
    fun sendVerificationEmail(to: String, token: String){
        val subject = "Fintrack Email Verification"
        val body = "Click this link to verify your email: https://yourapp.com/verify?token=$token"
        sendEmail(to, subject, body)
    }

    fun sendPasswordResetEmail(to: String, token: String) {
        val subject = "Fintrack Password Reset"
        val body = "Click this link to reset your password: https://yourapp.com/reset-password?token=$token"
        sendEmail(to, subject, body)
    }

    fun sendEmail(to: String, subject: String, body: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject(subject)
        message.setText(body)
        mailSender.send(message)
    }
}