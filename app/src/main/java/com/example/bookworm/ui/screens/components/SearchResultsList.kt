package com.example.bookworm.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bookworm.model.Book

@Composable
fun SearchResultsList(
    searchResults: List<Book>,
    onSearchResultClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.heightIn(max = 300.dp)
        ) {
            items(searchResults) { book ->
                ListItem(
                    headlineContent = { Text(book.title, fontWeight = FontWeight.Bold) },
                    supportingContent = { Text(book.author) },
                    modifier = Modifier.clickable { onSearchResultClick(book.id) }
                )
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }
        }
    }
}