package com.example.bookworm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.db.AppDatabase
import com.example.bookworm.data.repository.BookRepository
import com.example.bookworm.model.Book
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository


    private val allBooks: StateFlow<List<Book>>


    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedGenres = MutableStateFlow<Set<String>>(emptySet())
    val selectedGenres = _selectedGenres.asStateFlow()

    private val _selectedAuthors = MutableStateFlow<Set<String>>(emptySet())
    val selectedAuthors = _selectedAuthors.asStateFlow()


    val availableGenres: StateFlow<List<String>>
    val availableAuthors: StateFlow<List<String>>
    val searchResults: StateFlow<List<Book>>
    val filteredBooks: StateFlow<List<Book>>

    init {
        val bookDao = AppDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)


        allBooks = repository.allBooks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


        filteredBooks = combine(allBooks, _selectedGenres, _selectedAuthors) { books, genres, authors ->
            if (genres.isEmpty() && authors.isEmpty()) {
                books
            } else {
                books.filter { book ->
                    val genreMatch = genres.isEmpty() || book.genre in genres
                    val authorMatch = authors.isEmpty() || book.author in authors
                    genreMatch && authorMatch
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


        searchResults = combine(allBooks, searchQuery) { books, query ->
            if (query.isBlank()) {
                emptyList()
            } else {
                books.filter { book ->
                    book.title.contains(query, ignoreCase = true) || book.author.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())




        availableGenres = allBooks.map { books ->
            books.map { it.genre }.distinct().sorted()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        availableAuthors = allBooks.map { books ->
            books.map { it.author }.distinct().sorted()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun onSearchQueryChanged(query: String) { _searchQuery.value = query }

    fun onGenreSelected(genre: String) {
        _selectedGenres.update { currentGenres ->
            currentGenres.toMutableSet().apply {
                if (genre in this) remove(genre) else add(genre)
            }
        }
    }

    fun onAuthorSelected(author: String) {
        _selectedAuthors.update { currentAuthors ->
            currentAuthors.toMutableSet().apply {
                if (author in this) remove(author) else add(author)
            }
        }
    }

    fun clearFilters() {
        _selectedGenres.value = emptySet()
        _selectedAuthors.value = emptySet()
    }

    fun addBook(book: Book) { viewModelScope.launch { repository.insert(book) } }
    fun deleteBook(book: Book) { viewModelScope.launch { repository.delete(book) } }
}