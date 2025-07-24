package com.example.bookworm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.db.AppDatabase
import com.example.bookworm.data.repository.BookRepository
import com.example.bookworm.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: BookRepository
) : ViewModel() {

    private val bookId: Int = checkNotNull(savedStateHandle["bookId"])

    val book: StateFlow<Book?> = repository.getBookById(bookId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: androidx.lifecycle.viewmodel.CreationExtras
                ): T {
                    val savedStateHandle = extras.createSavedStateHandle()
                    val database = AppDatabase.getDatabase(application)
                    val repository = BookRepository(database.bookDao())
                    return BookDetailViewModel(savedStateHandle, repository) as T
                }
            }
    }

    // SIMPLIFIED: borrowBook now only takes the return date.
    fun borrowBook(returnDateMillis: Long) {
        viewModelScope.launch {
            book.value?.let { currentBook ->
                val updatedBook = currentBook.copy(borrowedUntil = returnDateMillis)
                repository.update(updatedBook)
            }
        }
    }

    fun returnBook() {
        viewModelScope.launch {
            book.value?.let { currentBook ->
                val updatedBook = currentBook.copy(borrowedUntil = null)
                repository.update(updatedBook)
            }
        }
    }
}