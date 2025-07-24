package com.example.bookworm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val genre: String,
    val year: Int,
    val review: String? = null,
    val borrowedUntil: Long? = null,
    val imageUri: String? = null
)