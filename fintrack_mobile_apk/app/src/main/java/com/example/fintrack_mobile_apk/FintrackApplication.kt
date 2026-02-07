package com.example.fintrack_mobile_apk

import android.app.Application
import com.example.fintrack_mobile_apk.data.FintrackDatabase
import com.example.fintrack_mobile_apk.data.FintrackRepository
import com.example.fintrack_mobile_apk.data.SettingsRepository

/**
 * The main application class for the Fintrack app.
 * This class is responsible for creating and providing singleton instances of the database and repository.
 */
class FintrackApplication : Application() {

    /**
     * The application-wide instance of the [FintrackDatabase].
     */
    val database by lazy { FintrackDatabase.getDatabase(this) }

    /**
     * The application-wide instance of the [FintrackRepository].
     */
    val repository by lazy { FintrackRepository(database.accountDao(), database.transactionDao()) }

    /**
     * The application-wide instance of the [SettingsRepository].
     */
    val settingsRepository by lazy { SettingsRepository(this) }
}
