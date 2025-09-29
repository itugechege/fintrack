package com.fintrack.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class BackendApplication

//@EnableAsync // Enables Spring's @Async support
fun main(args: Array<String>) {
	println("🚀 Starting Spring Boot Kotlin + Actuator App")
	runApplication<BackendApplication>(*args)
}
