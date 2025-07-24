package com.example.bookworm.data.repository

import com.example.bookworm.data.db.BookDao
import com.example.bookworm.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    val borrowedBooks: Flow<List<Book>> = bookDao.getBorrowedBooks()

    suspend fun insert(book: Book) {
        println("DEBUG: Repository.insert called with book: $book")
        try {
            bookDao.insertBook(book)
            println("DEBUG: bookDao.insertBook completed successfully")
        } catch (e: Exception) {
            println("ERROR: Exception in repository.insert: ${e.message}")
            throw e
        }
    }

    fun getBookById(bookId: Int): Flow<Book?> {
        println("DEBUG: Repository.getBookById called with ID: $bookId")
        return bookDao.getBookById(bookId)
    }
    suspend fun update(book: Book) {
        println("DEBUG: Repository.update called with book: $book")
        try {
            bookDao.updateBook(book)
            println("DEBUG: bookDao.updateBook completed successfully")
        } catch (e: Exception) {
            println("ERROR: Exception in repository.update: ${e.message}")
            throw e
        }
    }
    suspend fun delete(book: Book) {
        bookDao.deleteBook(book)
    }

}