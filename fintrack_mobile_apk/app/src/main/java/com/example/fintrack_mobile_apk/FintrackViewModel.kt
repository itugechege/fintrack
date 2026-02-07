package com.example.fintrack_mobile_apk

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fintrack_mobile_apk.data.Account
import com.example.fintrack_mobile_apk.data.FintrackRepository
import com.example.fintrack_mobile_apk.data.SettingsRepository
import com.example.fintrack_mobile_apk.data.Transaction
import com.example.fintrack_mobile_apk.sms.SmsService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the Fintrack app.
 * This class provides data to the UI and survives configuration changes. It communicates with the
 * [FintrackRepository] to fetch and update data.
 *
 * @param repository The repository for accessing app data.
 * @param settingsRepository The repository for accessing user settings.
 * @param smsService The service for reading and parsing SMS messages.
 */
class FintrackViewModel(private val repository: FintrackRepository, private val settingsRepository: SettingsRepository, private val smsService: SmsService) : ViewModel() {

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
     * A [StateFlow] of all transactions that need clarification.
     */
    val transactionsForClarification = allTransactions.map {
        it.filter { transaction -> transaction.needsClarification }
    }.stateIn(
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
     * A [StateFlow] that emits `true` if the user has completed the setup process, and `false` otherwise.
     */
    val setupComplete = settingsRepository.setupComplete.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
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

    /**
     * Marks the setup process as complete.
     */
    fun completeSetup() {
        viewModelScope.launch {
            settingsRepository.completeSetup()
        }
    }

    /**
     * Imports SMS transactions.
     */
    fun importSmsTransactions() {
        viewModelScope.launch {
            smsService.importSmsTransactions()
        }
    }

    /**
     * Updates a transaction with a new category.
     *
     * @param transaction The transaction to update.
     * @param category The new category for the transaction.
     */
    fun clarifyTransaction(transaction: Transaction, category: String) {
        viewModelScope.launch {
            repository.updateTransaction(transaction.copy(category = category, needsClarification = false))
        }
    }

    /**
     * Inserts a new account into the database.
     *
     * @param account The account to insert.
     */
    fun insertAccount(account: Account) {
        viewModelScope.launch {
            repository.insertAccount(account)
        }
    }

    /**
     * Inserts a new transaction into the database.
     *
     * @param transaction The transaction to insert.
     */
    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }
}

/**
 * Factory for creating instances of [FintrackViewModel].
 * This is required because the ViewModel has a constructor with arguments.
 *
 * @param repository The repository for accessing app data.
 * @param settingsRepository The repository for accessing user settings.
 * @param context The application context.
 */
class FintrackViewModelFactory(private val repository: FintrackRepository, private val settingsRepository: SettingsRepository, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FintrackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FintrackViewModel(repository, settingsRepository, SmsService(context, repository)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
