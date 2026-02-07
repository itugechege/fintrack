package com.example.fintrack_mobile_apk.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for managing user settings using Jetpack DataStore.
 * This class provides a way to store and retrieve user preferences, such as currency and location.
 *
 * @param context The application context.
 */
class SettingsRepository(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private object PreferencesKeys {
        val CURRENCY = stringPreferencesKey("currency")
        val LOCATION = stringPreferencesKey("location")
        val SETUP_COMPLETE = booleanPreferencesKey("setup_complete")
    }

    /**
     * The user's preferred currency.
     */
    val currency: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.CURRENCY] ?: "USD"
        }

    /**
     * The user's location.
     */
    val location: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LOCATION] ?: "US"
        }

    /**
     * A flow that emits `true` if the user has completed the setup process, and `false` otherwise.
     */
    val setupComplete: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SETUP_COMPLETE] ?: false
        }

    /**
     * Updates the user's preferred currency.
     *
     * @param currency The new currency to set.
     */
    suspend fun updateCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENCY] = currency
        }
    }

    /**
     * Updates the user's location.
     *
     * @param location The new location to set.
     */
    suspend fun updateLocation(location: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCATION] = location
        }
    }

    /**
     * Marks the setup process as complete.
     */
    suspend fun completeSetup() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SETUP_COMPLETE] = true
        }
    }
}
