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
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class AddEditBookViewModel(
    private val application: Application, // We need the application context for file operations
    savedStateHandle: SavedStateHandle,
    private val repository: BookRepository
) : ViewModel() {

    // State holders for all form fields
    val title = mutableStateOf("")
    val author = mutableStateOf("")
    val selectedGenre = mutableStateOf("Fiction")
    val selectedYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR).toString())
    val description = mutableStateOf("")
    val imageUri = mutableStateOf<Uri?>(null)

    private val bookId: Int? = savedStateHandle["bookId"]
    private var currentBook: Book? = null

    val isEditing = bookId != null
    // A flag to track if the image has been changed by the user in edit mode
    private var isImageChanged = false

    init {
        if (isEditing) {
            viewModelScope.launch {
                currentBook = repository.getBookById(bookId!!).firstOrNull()
                currentBook?.let { book ->
                    title.value = book.title
                    author.value = book.author
                    selectedGenre.value = book.genre
                    selectedYear.value = book.year.toString()
                    description.value = book.review ?: ""
                    imageUri.value = book.imageUri?.let { Uri.parse(it) }
                }
            }
        }
    }

    // New public function for the UI to call when the image URI changes
    fun onImageUriChanged(uri: Uri?) {
        isImageChanged = true
        imageUri.value = uri
    }

    fun saveBook(onSaveFinished: () -> Unit) {
        viewModelScope.launch {
            var finalImageUriString = if (isEditing) currentBook?.imageUri else null

            // --- START OF FIX ---
            // Only copy the image if it's new or has been changed by the user.
            // We check if the URI is a temporary "content://" URI from the gallery/camera.
            if (isImageChanged && imageUri.value != null && imageUri.value.toString().startsWith("content://")) {
                finalImageUriString = copyImageToInternalStorage(application, imageUri.value!!)
            } else if (isImageChanged) { // Handle case where image is set from camera (file://) or cleared
                finalImageUriString = imageUri.value?.toString()
            }
            // --- END OF FIX ---

            if (isEditing) {
                val updatedBook = currentBook!!.copy(
                    title = title.value.trim(),
                    author = author.value.trim(),
                    genre = selectedGenre.value,
                    year = selectedYear.value.toIntOrNull() ?: 0,
                    review = description.value.trim().takeIf { it.isNotBlank() },
                    imageUri = finalImageUriString
                )
                repository.update(updatedBook)
            } else {
                val newBook = Book(
                    title = title.value.trim(),
                    author = author.value.trim(),
                    genre = selectedGenre.value,
                    year = selectedYear.value.toIntOrNull() ?: 0,
                    review = description.value.trim().takeIf { it.isNotBlank() },
                    imageUri = finalImageUriString
                )
                repository.insert(newBook)
            }
            onSaveFinished()
        }
    }

    // --- THIS IS THE NEW CORE LOGIC FOR PERSISTENT IMAGES ---
    private suspend fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) { // Perform file operations on a background thread
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val fileName = "book_cover_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                // Return the URI string of the NEW, permanent file
                Uri.fromFile(file).toString()
            } catch (e: Exception) {
                e.printStackTrace()
                null // Return null if copying fails
            }
        }
    }
    // --- END OF NEW CORE LOGIC ---

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