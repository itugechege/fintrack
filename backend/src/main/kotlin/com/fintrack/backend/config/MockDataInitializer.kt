package com.fintrack.backend.config

import java.sql.DriverManager
import java.io.File

class MockDataInitializer {

    private var dbUrl: String = ""
    private var dbUser: String = ""
    private var dbPassword: String = ""

    fun configureDatabase(url: String, user: String, password: String) {
        dbUrl = url
        dbUser = user
        dbPassword = password
    }

    fun runMockData() {
        if (dbUrl.isEmpty() || dbUser.isEmpty() || dbPassword.isEmpty()) {
            println("Database credentials not configured.")
            return
        }

        if (!isDatabaseEmpty()) {
            println("Database already contains data. Skipping mock data population.")
            return
        }

        println("Database is empty. Running mock data Python script...")

        val pythonScriptPath = File("scripts/populate_mock_data.py").absolutePath
        val pythonCommand = if (System.getProperty("os.name").lowercase().contains("windows")) "python" else "python3"

        try {
            val processBuilder = ProcessBuilder(pythonCommand, pythonScriptPath)
            processBuilder.inheritIO() // show script output in console
            val process = processBuilder.start()
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("Mock data populated successfully!")
            } else {
                System.err.println("Mock data script failed with exit code: $exitCode")
            }
        } catch (e: Exception) {
            System.err.println("Error running mock data script: ${e.message}")
        }
    }

    private fun isDatabaseEmpty(): Boolean {
        return try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword).use { conn ->
                conn.prepareStatement("SELECT COUNT(*) FROM users").use { stmt ->
                    stmt.executeQuery().use { rs ->
                        rs.next() && rs.getInt(1) == 0
                    }
                }
            }
        } catch (e: Exception) {
            println("Error checking database: ${e.message}")
            true
        }
    }
}
