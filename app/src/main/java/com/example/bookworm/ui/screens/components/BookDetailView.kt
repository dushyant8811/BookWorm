package com.example.bookworm.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookworm.model.Book
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(
    book: Book,
    onBorrow: (Long) -> Unit,
    onReturn: () -> Unit,
    paddingValues: PaddingValues, // Parameter from the Scaffold
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val showBottomSheetState = remember { mutableStateOf(false) }
    var titlePositionY by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    Column(
        // --- START OF FIX ---
        // We now apply the full padding from the Scaffold.
        // This adds padding to the top for the status bar AND to the bottom for the navigation bar.
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
        // --- END OF FIX ---
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    // This calculation remains the same, but is now more accurate because the
                    // coordinate system of our column starts in the correct place.
                    val topPaddingPx = with(density) { paddingValues.calculateTopPadding().toPx() }
                    val fadeOutDistance = titlePositionY - topPaddingPx
                    if (fadeOutDistance > 0) {
                        val progress = (scrollState.value / fadeOutDistance)
                        alpha = (1f - progress).coerceIn(0f, 1f)
                    }
                }
                .padding(top = 24.dp) // This is extra padding just for aesthetics
        ) {
            AsyncImage(
                model = book.imageUri,
                contentDescription = book.title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.75f)
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // The content column no longer needs its own top padding, as the parent handles it.
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    if (titlePositionY == 0f) {
                        titlePositionY = coordinates.positionInWindow().y
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(book.author, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(" • ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(book.genre, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(" • ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(book.year.toString(), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(24.dp))
            book.review?.let { reviewText ->
                Text(
                    text = reviewText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontStyle = FontStyle.Italic,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (book.borrowedUntil == null) {
                Button(
                    onClick = { showBottomSheetState.value = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Borrow Book", fontSize = 16.sp)
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Borrowed",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Return by: ${formatDate(book.borrowedUntil)}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Button(
                            onClick = { onReturn() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.KeyboardReturn, contentDescription = "Return")
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text("Return")
                        }
                    }
                }
            }
            // The final spacer is no longer needed as the parent padding handles it.
            // Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showBottomSheetState.value) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetState.value = false },
            sheetState = sheetState,
            windowInsets = WindowInsets.navigationBars
        ) {
            BorrowSheetContent(
                onDone = { returnDate ->
                    showBottomSheetState.value = false
                    onBorrow(returnDate)
                }
            )
        }
    }
}

// CORRECTED: This is now a top-level function in the file
private fun formatDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BorrowSheetContent(
    onDone: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val today = calendar.timeInMillis

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today,
        initialDisplayedMonthMillis = today,
        yearRange = (currentYear..currentYear),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val fifteenDaysInMillis = 14 * 24 * 60 * 60 * 1000L
                val maxSelectableDate = today + fifteenDaysInMillis
                return utcTimeMillis in today..maxSelectableDate
            }
        }
    )

    val selectedEndDate = datePickerState.selectedDateMillis
    val durationInDays = remember(selectedEndDate) {
        if (selectedEndDate != null) {
            val durationMillis = selectedEndDate - today
            (durationMillis / (1000 * 60 * 60 * 24)) + 1
        } else {
            0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select a return date", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Borrowing Date", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(formatDate(today), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color(0xFF006400))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Duration", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                AssistChip(
                    onClick = { /* Display only */ },
                    label = { Text(if (durationInDays > 0) "$durationInDays Days" else "-", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.Timer, "Duration", Modifier.size(AssistChipDefaults.IconSize)) }
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Return Date", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                AssistChip(
                    onClick = { /* Do nothing, chip is for display */ },
                    label = { Text(datePickerState.selectedDateMillis?.let { formatDate(it) } ?: "Select a date", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold) },
                    colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.errorContainer, labelColor = MaterialTheme.colorScheme.onErrorContainer),
                    border = null
                )
            }
        }
        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onDone(datePickerState.selectedDateMillis!!) },
            enabled = datePickerState.selectedDateMillis != null,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Done", fontSize = 16.sp)
        }
    }
}