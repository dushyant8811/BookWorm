package com.example.bookworm.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookworm.ui.screens.components.BookDetailView
import com.example.bookworm.ui.viewmodel.BookDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(navController: NavController) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: BookDetailViewModel = viewModel(factory = BookDetailViewModel.Factory(application))

    val book by viewModel.book.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "Book Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        book?.let {
                            navController.navigate("addBook?bookId=${it.id}")
                        }
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Book")
                    }
                }
            )
        }
    ) { paddingValues -> // We get the paddingValues from the Scaffold
        Box(
            modifier = Modifier
                .fillMaxSize(),
                    // IMPORTANT: We do NOT apply the padding here. We pass it down.
                    // .padding(paddingValues),
                    contentAlignment = Alignment.Center
        ) {
            book?.let {
                BookDetailView(
                    book = it,
                    onBorrow = { returnDate -> viewModel.borrowBook(returnDate) },
                    onReturn = { viewModel.returnBook() },
                    // --- START OF CHANGE ---
                    // Pass the paddingValues down to the detail view
                    paddingValues = paddingValues
                    // --- END OF CHANGE ---
                )
            } ?: CircularProgressIndicator()
        }
    }
}