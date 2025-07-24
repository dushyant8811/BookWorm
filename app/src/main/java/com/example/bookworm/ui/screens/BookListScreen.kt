package com.example.bookworm.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookworm.model.Book
import com.example.bookworm.ui.screens.components.BookGridItem
import com.example.bookworm.ui.screens.components.FilterDialog
import com.example.bookworm.ui.screens.components.HomeTopBar
import com.example.bookworm.ui.screens.components.SearchResultsList
import com.example.bookworm.ui.theme.BookWormTheme
import com.example.bookworm.ui.viewmodel.BookListViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    navController: NavController,
    bookListViewModel: BookListViewModel = viewModel(),
    onBookClick: (Int) -> Unit
) {
    val filteredBookList by bookListViewModel.filteredBooks.collectAsState()
    var bookToDelete by remember { mutableStateOf<Book?>(null) }
    val searchQuery by bookListViewModel.searchQuery.collectAsState()
    val searchResults by bookListViewModel.searchResults.collectAsState()
    var isSearchActive by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            HomeTopBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = bookListViewModel::onSearchQueryChanged,
                onFocusChanged = { isFocused -> isSearchActive = isFocused },
                onFilterClick = { showFilterDialog = true }
            )
        },
        // This line will now work correctly
        bottomBar = { AppBottomNavigation(currentRoute = "bookList", navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addBook") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary )
                {
                Icon(Icons.Filled.Add, contentDescription = "Add Book")
            }
        },

        containerColor = Color(0xFFF5F5F5)

    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (filteredBookList.isEmpty() && bookListViewModel.selectedGenres.value.isEmpty() && bookListViewModel.selectedAuthors.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No books in your library.",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Tap the '+' button to add one!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (filteredBookList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No books match your filters.", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredBookList) { book ->
                        BookGridItem(
                            book = book,
                            onClick = { onBookClick(book.id) },
                            onLongClick = { bookToDelete = book }
                        )
                    }
                }
            }

            if (isSearchActive && searchQuery.isNotBlank()) {
                SearchResultsList(
                    searchResults = searchResults,
                    onSearchResultClick = { bookId ->
                        isSearchActive = false
                        bookListViewModel.onSearchQueryChanged("") // Clear search on click
                        navController.navigate("bookDetail/$bookId")
                    }
                )
            }
        }
    }

    bookToDelete?.let { book ->
        AlertDialog(
            onDismissRequest = { bookToDelete = null },
            title = { Text("Remove Book?") },
            text = { Text("Are you sure you want to permanently remove \"${book.title}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        bookListViewModel.deleteBook(book)
                        bookToDelete = null
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(onClick = { bookToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showFilterDialog) {
        val availableGenres by bookListViewModel.availableGenres.collectAsState()
        val selectedGenres by bookListViewModel.selectedGenres.collectAsState()
        val availableAuthors by bookListViewModel.availableAuthors.collectAsState()
        val selectedAuthors by bookListViewModel.selectedAuthors.collectAsState()

        FilterDialog(
            availableGenres = availableGenres,
            selectedGenres = selectedGenres,
            onGenreSelected = bookListViewModel::onGenreSelected,
            availableAuthors = availableAuthors,
            selectedAuthors = selectedAuthors,
            onAuthorSelected = bookListViewModel::onAuthorSelected,
            onDismiss = { showFilterDialog = false },
            onClear = { bookListViewModel.clearFilters() }
        )
    }
}

// --- THIS IS THE MISSING COMPOSABLE ---
// It is now added back to the file.
// In app/src/main/java/com/example/bookworm/ui/screens/BookListScreen.kt

@Composable
fun AppBottomNavigation(currentRoute: String?, navController: NavController) {
    NavigationBar(
        modifier = Modifier.height(105.dp),
        // FINAL: Very light gray for bottom nav
        containerColor = Color(0xFFF5F5F5)
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary, // White icon when selected
            selectedTextColor = MaterialTheme.colorScheme.primary,   // Dark Gray text when selected
            indicatorColor = MaterialTheme.colorScheme.primary,      // Dark Gray pill background
            unselectedIconColor = MaterialTheme.colorScheme.secondary, // Medium Gray icon when not selected
            unselectedTextColor = MaterialTheme.colorScheme.secondary  // Medium Gray text when not selected
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "bookList",
            onClick = {
                if (currentRoute != "bookList") {
                    navController.navigate("bookList") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            colors = navItemColors
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Bookmark, contentDescription = "Borrowed") },
            label = { Text("Borrowed") },
            selected = currentRoute == "borrowedBooks",
            onClick = {
                if (currentRoute != "borrowedBooks") {
                    navController.navigate("borrowedBooks") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            colors = navItemColors
        )
    }
}
// --- END OF THE MISSING COMPOSABLE ---

@Preview(showSystemUi = true)
@Composable
fun BookListScreenPreview() {
    BookWormTheme {
        BookListScreen(navController = rememberNavController(), onBookClick = {})
    }
}