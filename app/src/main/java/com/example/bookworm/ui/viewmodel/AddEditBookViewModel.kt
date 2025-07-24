package com.example.bookworm.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.db.AppDatabase
import com.example.bookworm.data.repository.BookRepository
import com.example.bookworm.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class AddEditBookViewModel(
    private val application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: BookRepository
) : ViewModel() {


    val title = mutableStateOf("")
    val author = mutableStateOf("")
    val selectedGenre = mutableStateOf("Fiction")
    val selectedYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR).toString())
    val description = mutableStateOf("")
    val imageUri = mutableStateOf<Uri?>(null)

    private val bookId: Int? = savedStateHandle["bookId"]
    private var currentBook: Book? = null

    val isEditing = bookId != null && bookId != -1

    private var isImageChanged = false


    val isLoading = mutableStateOf(false)

    init {
        println("DEBUG: ViewModel init - isEditing: $isEditing, bookId: $bookId")

        if (isEditing && bookId != null) {
            println("DEBUG: Loading book with ID: $bookId")
            isLoading.value = true

            viewModelScope.launch {
                try {

                    withTimeout(10000) {
                        println("DEBUG: About to call repository.getBookById($bookId)")
                        val bookFlow = repository.getBookById(bookId)
                        println("DEBUG: Got flow, calling firstOrNull()")
                        currentBook = bookFlow.firstOrNull()
                        println("DEBUG: Loaded currentBook: $currentBook")

                        currentBook?.let { book ->
                            println("DEBUG: Populating fields with book data")
                            title.value = book.title
                            author.value = book.author
                            selectedGenre.value = book.genre
                            selectedYear.value = book.year.toString()
                            description.value = book.review ?: ""
                            book.imageUri?.let {
                                imageUri.value = Uri.parse(it)
                                println("DEBUG: Set image URI: $it")
                            }
                            println("DEBUG: All fields populated successfully")
                        } ?: run {
                            println("ERROR: Failed to load book with ID: $bookId - book not found in database")
                        }
                    }
                } catch (e: Exception) {
                    println("ERROR: Exception loading book: ${e.message}")
                    println("ERROR: Exception type: ${e::class.java.simpleName}")
                    e.printStackTrace()
                } finally {
                    isLoading.value = false
                    println("DEBUG: Book loading completed, isLoading set to false")
                }
            }
        } else {
            println("DEBUG: Not in edit mode or bookId is null")
        }
    }

    fun onImageUriChanged(uri: Uri?) {
        isImageChanged = true
        imageUri.value = uri
    }

    fun saveBook(onSaveFinished: () -> Unit) {
        viewModelScope.launch {
            try {
                println("DEBUG: Starting to save book: ${title.value}")
                println("DEBUG: Author: ${author.value}")
                println("DEBUG: Genre: ${selectedGenre.value}")
                println("DEBUG: Year: ${selectedYear.value}")
                println("DEBUG: IsEditing: $isEditing")
                println("DEBUG: CurrentBook: $currentBook")


                if (isEditing && isLoading.value) {
                    println("DEBUG: Still loading book data, cannot save yet")
                    return@launch
                }


                println("DEBUG: Processing image URI...")
                val finalImageUriString: String? = when {

                    isImageChanged -> {
                        println("DEBUG: Image was changed")
                        imageUri.value?.let { uri ->
                            println("DEBUG: Copying image to internal storage...")

                            copyImageToInternalStorage(application, uri)
                        }

                    }

                    isEditing -> {
                        println("DEBUG: Editing mode, keeping existing image")
                        currentBook?.imageUri
                    }

                    else -> {
                        println("DEBUG: Adding new book, no image")
                        null
                    }
                }
                println("DEBUG: Final image URI: $finalImageUriString")


                if (isEditing) {
                    println("DEBUG: Updating existing book...")
                    currentBook?.let { book ->
                        println("DEBUG: Current book ID: ${book.id}")
                        val updatedBook = book.copy(
                            title = title.value.trim(),
                            author = author.value.trim(),
                            genre = selectedGenre.value,
                            year = selectedYear.value.toIntOrNull() ?: 0,
                            review = description.value.trim().takeIf { it.isNotBlank() },
                            imageUri = finalImageUriString
                        )
                        println("DEBUG: Updated book object: $updatedBook")
                        println("DEBUG: About to call repository.update...")
                        repository.update(updatedBook)
                        println("DEBUG: Repository.update completed successfully")
                    } ?: run {

                        println("ERROR: currentBook is null in edit mode! Attempting to reload...")
                        if (bookId != null) {
                            try {
                                val reloadedBook = withTimeout(5000) {
                                    repository.getBookById(bookId).firstOrNull()
                                }
                                if (reloadedBook != null) {
                                    println("DEBUG: Successfully reloaded book: $reloadedBook")
                                    val updatedBook = reloadedBook.copy(
                                        title = title.value.trim(),
                                        author = author.value.trim(),
                                        genre = selectedGenre.value,
                                        year = selectedYear.value.toIntOrNull() ?: 0,
                                        review = description.value.trim().takeIf { it.isNotBlank() },
                                        imageUri = finalImageUriString
                                    )
                                    repository.update(updatedBook)
                                    println("DEBUG: Repository.update completed successfully after reload")
                                } else {
                                    println("ERROR: Could not reload book with ID: $bookId")
                                    throw Exception("Book not found for update")
                                }
                            } catch (e: Exception) {
                                println("ERROR: Failed to reload book: ${e.message}")
                                throw e
                            }
                        } else {
                            throw Exception("Cannot update: bookId is null")
                        }
                    }
                } else {
                    println("DEBUG: Creating new book...")
                    val newBook = Book(
                        title = title.value.trim(),
                        author = author.value.trim(),
                        genre = selectedGenre.value,
                        year = selectedYear.value.toIntOrNull() ?: 0,
                        review = description.value.trim().takeIf { it.isNotBlank() },
                        imageUri = finalImageUriString
                    )
                    println("DEBUG: New book object: $newBook")
                    println("DEBUG: About to call repository.insert...")
                    repository.insert(newBook)
                    println("DEBUG: Repository.insert completed successfully")
                }

                println("DEBUG: About to call onSaveFinished...")

                onSaveFinished()
                println("DEBUG: onSaveFinished called successfully")

            } catch (e: Exception) {

                println("ERROR: Exception during save: ${e.message}")
                println("ERROR: Exception type: ${e::class.java.simpleName}")
                println("ERROR: Stack trace:")
                e.printStackTrace()

            }
        }
    }

    private suspend fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream == null) {
                    return@withContext null
                }

                val fileName = "book_cover_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }


                Uri.fromFile(file).toString()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

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
                    return AddEditBookViewModel(application, savedStateHandle, repository) as T
                }
            }
    }
}