package com.fintrack.backend

import com.fintrack.backend.config.MockDataInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class BackendApplication

//@EnableAsync // Enables Spring's @Async support
fun main(args: Array<String>) {
    // Configure database manually (same as Spring Boot)
    val dbUrl = "jdbc:postgresql://localhost:5432/dev_db"
    val dbUser = "admin"
    val dbPassword = "root"
    val mockDataInitializer = MockDataInitializer()


    println("🚀 Starting Spring Boot Kotlin + Actuator App")
	runApplication<BackendApplication>(*args)

    mockDataInitializer.configureDatabase(dbUrl, dbUser, dbPassword)
    mockDataInitializer.runMockData()
}




