package com.example.bookworm.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    availableGenres: List<String>,
    selectedGenres: Set<String>,
    onGenreSelected: (String) -> Unit,
    availableAuthors: List<String>,
    selectedAuthors: Set<String>,
    onAuthorSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onClear: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter Books") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                // Genres Section
                Text("By Genre", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    availableGenres.forEach { genre ->
                        FilterChip(
                            selected = genre in selectedGenres,
                            onClick = { onGenreSelected(genre) },
                            label = { Text(genre) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Authors Section
                Text("By Author", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    availableAuthors.forEach { author ->
                        FilterChip(
                            selected = author in selectedAuthors,
                            onClick = { onAuthorSelected(author) },
                            label = { Text(author) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onClear,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Clear")
            }
        }
    )
}