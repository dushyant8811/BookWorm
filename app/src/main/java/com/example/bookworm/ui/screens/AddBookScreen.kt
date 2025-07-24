package com.example.bookworm.ui.screens

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path // <-- THE CORRECT IMPORT FOR DRAWING
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookworm.ui.viewmodel.AddEditBookViewModel
import kotlinx.coroutines.delay
import java.io.File
import java.util.Calendar

sealed class UploadState {
    object Idle : UploadState()
    data class Uploading(val progress: Float) : UploadState()
    object Finished : UploadState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    navController: NavController
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: AddEditBookViewModel = viewModel(factory = AddEditBookViewModel.Factory(application))

    val context = LocalContext.current
    val showImageSourceDialogState = remember { mutableStateOf(false) }
    val uploadState = remember { mutableStateOf<UploadState>(UploadState.Idle) }

    var uploadKey by remember { mutableStateOf(0) }

    LaunchedEffect(uploadKey) {
        if (uploadState.value is UploadState.Uploading) {
            var progress = 0f
            while (progress < 1f) {
                delay(50)
                progress += 0.02f
                uploadState.value = UploadState.Uploading(progress.coerceAtMost(1f))
            }
            uploadState.value = UploadState.Finished
        }
    }


    val isFormValid = viewModel.title.value.isNotBlank() && viewModel.author.value.isNotBlank()
    val genreExpandedState = remember { mutableStateOf(false) }
    val genres = listOf("Fiction", "Dystopian", "Classic", "Mystery", "Fantasy", "Sci-Fi", "Non-Fiction")
    val yearExpandedState = remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                viewModel.onImageUriChanged(uri)
                uploadState.value = UploadState.Uploading(0f)
                uploadKey++ // This will trigger the LaunchedEffect once
            }
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                uploadState.value = UploadState.Uploading(0f)
                uploadKey++ // This will trigger the LaunchedEffect once
            } else {
                viewModel.onImageUriChanged(null)
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val uri = createImageUri(context)
                viewModel.onImageUriChanged(uri)
                cameraLauncher.launch(uri)
                // Don't set upload state here, let cameraLauncher handle it
            } else {
                Toast.makeText(context, "Camera permission is required.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear downTo 1900).map { it.toString() }

    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        cursorColor = MaterialTheme.colorScheme.primary
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (viewModel.isEditing) "Edit Book" else "Add Book", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ImageUploadBox(
                uploadState = uploadState.value,
                imageUri = viewModel.imageUri.value,
                onClick = {
                    if (uploadState.value is UploadState.Uploading) {
                        return@ImageUploadBox
                    }
                    showImageSourceDialogState.value = true
                }
            )

            TextField(value = viewModel.title.value, onValueChange = { viewModel.title.value = it }, label = { Text("Book Title") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
            TextField(value = viewModel.author.value, onValueChange = { viewModel.author.value = it }, label = { Text("Author's Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ExposedDropdownMenuBox(expanded = genreExpandedState.value, onExpandedChange = { genreExpandedState.value = !it }, modifier = Modifier.weight(1f)) {
                    TextField(value = viewModel.selectedGenre.value, onValueChange = {}, label = { Text("Genre") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpandedState.value) }, modifier = Modifier.menuAnchor(), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
                    ExposedDropdownMenu(expanded = genreExpandedState.value, onDismissRequest = { genreExpandedState.value = false }) {
                        genres.forEach { genre -> DropdownMenuItem(text = { Text(genre) }, onClick = { viewModel.selectedGenre.value = genre; genreExpandedState.value = false }) }
                    }
                }
                ExposedDropdownMenuBox(expanded = yearExpandedState.value, onExpandedChange = { yearExpandedState.value = !it }, modifier = Modifier.weight(1f)) {
                    TextField(value = viewModel.selectedYear.value, onValueChange = {}, label = { Text("Year") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpandedState.value) }, modifier = Modifier.menuAnchor(), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
                    ExposedDropdownMenu(expanded = yearExpandedState.value, onDismissRequest = { yearExpandedState.value = false }) {
                        years.forEach { year -> DropdownMenuItem(text = { Text(year) }, onClick = { viewModel.selectedYear.value = year; yearExpandedState.value = false }) }
                    }
                }
            }
            TextField(value = viewModel.description.value, onValueChange = { viewModel.description.value = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth().height(150.dp), shape = RoundedCornerShape(12.dp), colors = textFieldColors)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { viewModel.saveBook { navController.popBackStack() } }, enabled = isFormValid, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                Text("Save Book")
            }
        }
    }

    if (showImageSourceDialogState.value) {
        ImageSourcePickerDialog(
            onDismiss = { showImageSourceDialogState.value = false },
            onCameraClick = {
                showImageSourceDialogState.value = false
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                        val uri = createImageUri(context)
                        viewModel.onImageUriChanged(uri)
                        cameraLauncher.launch(uri)
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            },
            onGalleryClick = {
                showImageSourceDialogState.value = false
                galleryLauncher.launch("image/*")
            }
        )
    }
}

private fun createImageUri(context: Context): Uri {
    val imageFile = File.createTempFile("camera_photo_", ".jpg", context.cacheDir)
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
}

// THIS IS THE SINGLE, CORRECT VERSION OF THIS COMPOSABLE
@Composable
fun ImageUploadBox(
    uploadState: UploadState,
    imageUri: Uri?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (uploadState) {
            is UploadState.Idle -> {
                DashedBorderBox()
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.FileUpload, contentDescription = "Upload Icon", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                    Text("Tap to upload", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                }
            }
            is UploadState.Uploading -> {
                ProgressBorderCanvas(progress = uploadState.progress)
                Text(
                    text = "${(uploadState.progress * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            is UploadState.Finished -> {
                AsyncImage(model = imageUri, contentDescription = "Selected book cover", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
        }
    }
}

@Composable
fun ProgressBorderCanvas(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 300),
        label = "progressAnimation"
    )

    // Get the colors in the composable context
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val outlineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier.fillMaxSize()) {
        val strokeWidth = 2.dp.toPx()
        val cornerRadiusValue = 12.dp.toPx()
        val cornerRadius = CornerRadius(cornerRadiusValue)

        // Draw background
        drawRoundRect(
            color = surfaceVariantColor,
            cornerRadius = cornerRadius
        )

        // Draw border outline
        drawRoundRect(
            color = outlineColor,
            cornerRadius = CornerRadius(cornerRadiusValue),
            style = Stroke(width = strokeWidth),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = androidx.compose.ui.geometry.Size(
                size.width - strokeWidth,
                size.height - strokeWidth
            )
        )

        if (animatedProgress.value > 0f) {
            val topCenterX = size.width / 2
            val topCenterY = strokeWidth / 2
            val progressPath = Path()
            progressPath.moveTo(topCenterX, topCenterY)

            val width = size.width - strokeWidth
            val height = size.height - strokeWidth
            val left = strokeWidth / 2
            val top = strokeWidth / 2
            val right = left + width
            val bottom = top + height
            val radius = cornerRadiusValue

            val straightSides = 2 * (width - 2 * radius) + 2 * (height - 2 * radius)
            val cornerArcs = 4 * (Math.PI * radius / 2).toFloat()
            val totalPerimeter = straightSides + cornerArcs
            val targetDistance = totalPerimeter * animatedProgress.value
            var currentDistance = 0f
            val halfWidth = (width - 2 * radius) / 2

            // Top side (first half)
            if (targetDistance > currentDistance) {
                val segmentLength = halfWidth
                val drawDistance = kotlin.math.min(targetDistance - currentDistance, segmentLength)
                progressPath.lineTo(topCenterX + drawDistance, topCenterY)
                currentDistance += segmentLength

                // Top-right corner
                if (targetDistance > currentDistance) {
                    val arcLength = (Math.PI * radius / 2).toFloat()
                    val remainingInArc = kotlin.math.min(targetDistance - currentDistance, arcLength)
                    val sweepAngle = (remainingInArc / arcLength * 90f)
                    progressPath.arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = right - 2 * radius,
                            top = top,
                            right = right,
                            bottom = top + 2 * radius
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = sweepAngle,
                        forceMoveTo = false
                    )
                    currentDistance += arcLength

                    // Right side
                    if (targetDistance > currentDistance) {
                        val segmentLength = height - 2 * radius
                        val drawDistance = kotlin.math.min(targetDistance - currentDistance, segmentLength)
                        progressPath.lineTo(right, top + radius + drawDistance)
                        currentDistance += segmentLength

                        // Bottom-right corner
                        if (targetDistance > currentDistance) {
                            val arcLength = (Math.PI * radius / 2).toFloat()
                            val remainingInArc = kotlin.math.min(targetDistance - currentDistance, arcLength)
                            val sweepAngle = (remainingInArc / arcLength * 90f)
                            progressPath.arcTo(
                                rect = androidx.compose.ui.geometry.Rect(
                                    left = right - 2 * radius,
                                    top = bottom - 2 * radius,
                                    right = right,
                                    bottom = bottom
                                ),
                                startAngleDegrees = 0f,
                                sweepAngleDegrees = sweepAngle,
                                forceMoveTo = false
                            )
                            currentDistance += arcLength

                            // Bottom side
                            if (targetDistance > currentDistance) {
                                val segmentLength = width - 2 * radius
                                val drawDistance = kotlin.math.min(targetDistance - currentDistance, segmentLength)
                                progressPath.lineTo(right - radius - drawDistance, bottom)
                                currentDistance += segmentLength

                                // Bottom-left corner
                                if (targetDistance > currentDistance) {
                                    val arcLength = (Math.PI * radius / 2).toFloat()
                                    val remainingInArc = kotlin.math.min(targetDistance - currentDistance, arcLength)
                                    val sweepAngle = (remainingInArc / arcLength * 90f)
                                    progressPath.arcTo(
                                        rect = androidx.compose.ui.geometry.Rect(
                                            left = left,
                                            top = bottom - 2 * radius,
                                            right = left + 2 * radius,
                                            bottom = bottom
                                        ),
                                        startAngleDegrees = 90f,
                                        sweepAngleDegrees = sweepAngle,
                                        forceMoveTo = false
                                    )
                                    currentDistance += arcLength

                                    // Left side
                                    if (targetDistance > currentDistance) {
                                        val segmentLength = height - 2 * radius
                                        val drawDistance = kotlin.math.min(targetDistance - currentDistance, segmentLength)
                                        progressPath.lineTo(left, bottom - radius - drawDistance)
                                        currentDistance += segmentLength

                                        // Top-left corner
                                        if (targetDistance > currentDistance) {
                                            val arcLength = (Math.PI * radius / 2).toFloat()
                                            val remainingInArc = kotlin.math.min(targetDistance - currentDistance, arcLength)
                                            val sweepAngle = (remainingInArc / arcLength * 90f)
                                            progressPath.arcTo(
                                                rect = androidx.compose.ui.geometry.Rect(
                                                    left = left,
                                                    top = top,
                                                    right = left + 2 * radius,
                                                    bottom = top + 2 * radius
                                                ),
                                                startAngleDegrees = 180f,
                                                sweepAngleDegrees = sweepAngle,
                                                forceMoveTo = false
                                            )
                                            currentDistance += arcLength

                                            // Top side (second half)
                                            if (targetDistance > currentDistance) {
                                                val segmentLength = halfWidth
                                                val drawDistance = kotlin.math.min(targetDistance - currentDistance, segmentLength)
                                                progressPath.lineTo(left + radius + drawDistance, top)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Draw the progress path
            drawPath(
                path = progressPath,
                color = primaryColor,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}

@Composable
fun DashedBorderBox() {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRoundRect(
                    color = borderColor,
                    style = Stroke(width = 2.dp.toPx(), pathEffect = pathEffect),
                    cornerRadius = CornerRadius(12.dp.toPx())
                )
            }
    )
}

@Composable
fun ImageSourcePickerDialog(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choose Image Source") },
        text = { Text("Select a source for your book cover image.") },
        confirmButton = {
            TextButton(onClick = onCameraClick) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Camera", modifier = Modifier.padding(end = 8.dp))
                Text("Camera")
            }
        },
        dismissButton = {
            TextButton(onClick = onGalleryClick) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery", modifier = Modifier.padding(end = 8.dp))
                Text("Gallery")
            }
        }
    )
}