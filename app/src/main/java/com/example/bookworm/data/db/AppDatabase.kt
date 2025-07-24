package com.example.bookworm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bookworm.data.DataSource
import com.example.bookworm.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "book_database"
                )
                    // Add our new callback here
                    .addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // This is the callback that pre-populates the database
    private class AppDatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        // This method is called ONLY when the database is created for the first time
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                // We use a coroutine to insert data in the background
                CoroutineScope(SupervisorJob()).launch {
                    val bookDao = database.bookDao()
                    // Get the list of books from our DataSource
                    val books = DataSource.getPredefinedBooks(context.packageName)
                    // Insert them into the database
                    books.forEach { book ->
                        bookDao.insertBook(book)
                    }
                }
            }
        }
    }
}