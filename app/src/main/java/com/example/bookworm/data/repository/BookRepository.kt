package com.example.bookworm.data.repository

import com.example.bookworm.data.db.BookDao
import com.example.bookworm.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    val borrowedBooks: Flow<List<Book>> = bookDao.getBorrowedBooks()

    suspend fun insert(book: Book) {
        bookDao.insertBook(book)
    }

    fun getBookById(bookId: Int): Flow<Book?> {
        return bookDao.getBookById(bookId)
    }
    suspend fun update(book: Book) {
        bookDao.updateBook(book)
    }
    suspend fun delete(book: Book) {
        bookDao.deleteBook(book)
    }

}