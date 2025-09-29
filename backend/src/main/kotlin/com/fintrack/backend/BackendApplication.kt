package com.fintrack.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendApplication


fun main(args: Array<String>) {
	println("🚀 Starting Spring Boot Kotlin + Actuator App")
	runApplication<BackendApplication>(*args)
}
