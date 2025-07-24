package com.example.bookworm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookworm.ui.screens.*
import com.example.bookworm.ui.theme.BookWormTheme
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BookWormTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "bookList"
                    ) {
                        composable("bookList") {
                            BookListScreen(
                                navController = navController,
                                onBookClick = { bookId ->
                                    navController.navigate("bookDetail/$bookId")
                                }
                            )
                        }

                        composable(
                            route = "bookDetail/{bookId}",
                            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
                        ) {
                            BookDetailScreen(navController = navController)
                        }

                        composable(
                            route = "addBook?bookId={bookId}",
                            arguments = listOf(navArgument("bookId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            AddBookScreen(navController = navController)
                        }

                        composable("borrowedBooks") {
                            BorrowedBooksScreen(
                                navController = navController,
                                onBookClick = { bookId ->
                                    navController.navigate("bookDetail/$bookId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}