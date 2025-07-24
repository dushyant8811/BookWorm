package com.example.bookworm.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookworm.R
import com.example.bookworm.ui.theme.BookWormTheme
import com.example.bookworm.ui.theme.LightGray
import com.example.bookworm.ui.theme.White

@Composable
fun HomeTopBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onFilterClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top Row: Profile, Title, Mode Icon (Unchanged)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(44.dp), // A slightly larger size looks better for this icon
                tint = MaterialTheme.colorScheme.primary // Use the theme's primary color (Dark Gray)
            )
            Text(
                text = "BookWorm",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = { /* TODO: Implement dark mode toggle */ }) {
                Icon(
                    imageVector = Icons.Default.NightsStay,
                    contentDescription = "Toggle Dark Mode",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Just a simple TextField now. No more complex boxes.
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused) },

            placeholder = { Text("Search by title or author") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    // Show CLEAR button when typing
                    IconButton(onClick = {
                        onSearchQueryChanged("")
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear Search")
                    }
                } else {
                    // Show FILTER button when empty
                    IconButton(onClick = onFilterClick) {
                        Icon(Icons.Default.Tune, contentDescription = "Filter Books")
                    }
                }
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = White,
                focusedContainerColor = White
            )
        )
    }
}

