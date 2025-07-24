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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookById(bookId: Int): Flow<Book?>

    @Update
    suspend fun updateBook(book: Book)

    @Query("SELECT * FROM books WHERE borrowedUntil IS NOT NULL ORDER BY borrowedUntil ASC")
    fun getBorrowedBooks(): Flow<List<Book>>

    @Delete
    suspend fun deleteBook(book: Book)
}