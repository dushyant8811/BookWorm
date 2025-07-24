// In BookDao.kt
package com.example.bookworm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookworm.model.Book
import kotlinx.coroutines.flow.Flow
import androidx.room.Update
import androidx.room.Delete

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<Book>>

    // --- START OF FIX ---
    // Change the strategy to REPLACE. It's the most robust option for
    // an insert function where you might be handling objects that could
    // potentially conflict. It will insert if new, or replace if the
    // primary key matches.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)
    // --- END OF FIX ---

    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookById(bookId: Int): Flow<Book?>

    @Update
    suspend fun updateBook(book: Book)

    @Query("SELECT * FROM books WHERE borrowedUntil IS NOT NULL ORDER BY borrowedUntil ASC")
    fun getBorrowedBooks(): Flow<List<Book>>

    @Delete
    suspend fun deleteBook(book: Book)
}