package com.example.fintrack_mobile_apk.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the [Transaction] entity.
 * This interface defines the methods for interacting with the `transactions` table in the Room database.
 */
@Dao
interface TransactionDao {

    /**
     * Inserts a new transaction into the database. If the transaction already exists, it will be replaced.
     *
     * @param transaction The transaction to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    /**
     * Updates an existing transaction in the database.
     *
     * @param transaction The transaction to update.
     */
    @Update
    suspend fun update(transaction: Transaction)

    /**
     * Deletes a transaction from the database.
     *
     * @param transaction The transaction to delete.
     */
    @Delete
    suspend fun delete(transaction: Transaction)

    /**
     * Retrieves all transactions from the database, ordered by date in descending order.
     *
     * @return A [Flow] emitting a list of all transactions.
     */
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    /**
     * Retrieves a single transaction from the database by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return A [Flow] emitting the transaction with the specified ID, or null if it doesn't exist.
     */
    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionById(id: Long): Flow<Transaction?>
}
