package com.example.bookworm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.db.AppDatabase
import com.example.bookworm.data.repository.BookRepository
import com.example.bookworm.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class BorrowedBooksViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository
    val borrowedBooks: StateFlow<List<Book>>

    init {
        val bookDao = AppDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)
        borrowedBooks = repository.borrowedBooks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }
}