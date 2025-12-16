package com.example.fintrack_mobile_apk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fintrack_mobile_apk.data.FintrackRepository
import com.example.fintrack_mobile_apk.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the Fintrack app.
 * This class provides data to the UI and survives configuration changes. It communicates with the
 * [FintrackRepository] to fetch and update data.
 *
 * @param repository The repository for accessing app data.
 * @param settingsRepository The repository for accessing user settings.
 */
class FintrackViewModel(private val repository: FintrackRepository, private val settingsRepository: SettingsRepository) : ViewModel() {

    /**
     * A [StateFlow] of all accounts from the database.
     */
    val allAccounts = repository.allAccounts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * A [StateFlow] of all transactions from the database.
     */
    val allTransactions = repository.allTransactions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * A [StateFlow] of the user's preferred currency.
     */
    val currency = settingsRepository.currency.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "USD"
    )

    /**
     * Updates the user's preferred currency.
     *
     * @param currency The new currency to set.
     */
    fun updateCurrency(currency: String) {
        viewModelScope.launch {
            settingsRepository.updateCurrency(currency)
        }
    }
}

/**
 * Factory for creating instances of [FintrackViewModel].
 * This is required because the ViewModel has a constructor with arguments.
 *
 * @param repository The repository for accessing app data.
 * @param settingsRepository The repository for accessing user settings.
 */
class FintrackViewModelFactory(private val repository: FintrackRepository, private val settingsRepository: SettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FintrackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FintrackViewModel(repository, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
