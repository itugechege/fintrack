package com.example.fintrack_mobile_apk.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the [Account] entity.
 * This interface defines the methods for interacting with the `accounts` table in the Room database.
 */
@Dao
interface AccountDao {

    /**
     * Inserts a new account into the database. If the account already exists, it will be replaced.
     *
     * @param account The account to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)

    /**
     * Updates an existing account in the database.
     *
     * @param account The account to update.
     */
    @Update
    suspend fun update(account: Account)

    /**
     * Deletes an account from the database.
     *
     * @param account The account to delete.
     */
    @Delete
    suspend fun delete(account: Account)

    /**
     * Retrieves all accounts from the database, ordered by name.
     *
     * @return A [Flow] emitting a list of all accounts.
     */
    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun getAllAccounts(): Flow<List<Account>>

    /**
     * Retrieves a single account from the database by its ID.
     *
     * @param id The ID of the account to retrieve.
     * @return A [Flow] emitting the account with the specified ID, or null if it doesn't exist.
     */
    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getAccountById(id: Long): Flow<Account?>
}
