package com.example.fintrack_mobile_apk.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The main Room database for the Fintrack application.
 * This class defines the entities and DAOs that are part of the database.
 * It also includes a mechanism to pre-populate the database with mock data when it's first created.
 */
@Database(entities = [Account::class, Transaction::class], version = 1, exportSchema = false)
abstract class FintrackDatabase : RoomDatabase() {

    /**
     * Returns the [AccountDao] for interacting with the `accounts` table.
     */
    abstract fun accountDao(): AccountDao

    /**
     * Returns the [TransactionDao] for interacting with the `transactions` table.
     */
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: FintrackDatabase? = null

        /**
         * Gets the singleton instance of the [FintrackDatabase].
         *
         * @param context The application context.
         * @return The singleton instance of the [FintrackDatabase].
         */
        fun getDatabase(context: Context): FintrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FintrackDatabase::class.java,
                    "fintrack_database"
                )
                    .addCallback(FintrackDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * A callback to pre-populate the database with mock data when it's first created.
     */
    private class FintrackDatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.accountDao(), database.transactionDao())
                }
            }
        }

        /**
         * Populates the database with mock data.
         */
        suspend fun populateDatabase(accountDao: AccountDao, transactionDao: TransactionDao) {
            // Add mock accounts
            accountDao.insert(Account(name = "Checking Account", type = "ASSET", balance = 5420.50, balanceColor = 0xFF000000))
            accountDao.insert(Account(name = "Savings Account", type = "ASSET", balance = 12500.00, balanceColor = 0xFF000000))
            accountDao.insert(Account(name = "Credit Card", type = "LIABILITY", balance = -1250.75, balanceColor = 0xFFD97706))
            accountDao.insert(Account(name = "Salary", type = "INCOME", balance = 6500.00, balanceColor = 0xFF000000))
            accountDao.insert(Account(name = "Groceries", type = "EXPENSE", balance = 450.30, balanceColor = 0xFF000000))
            accountDao.insert(Account(name = "Rent", type = "EXPENSE", balance = 1500.00, balanceColor = 0xFF000000))

            // Add mock transactions
            transactionDao.insert(Transaction(name = "Monthly Salary", date = System.currentTimeMillis(), category = "Salary", amount = 6500.00))
            transactionDao.insert(Transaction(name = "Rent Payment", date = System.currentTimeMillis(), category = "Rent", amount = 1500.00))
        }
    }
}
