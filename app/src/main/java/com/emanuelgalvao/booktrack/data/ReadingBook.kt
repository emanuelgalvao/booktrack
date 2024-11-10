package com.emanuelgalvao.booktrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_book")
data class ReadingBook(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val title: String,
    val subtitle: String,
    val author: String,
    val totalPages: String,
    val description: String,
    val isReading: Boolean,
    val currentPage: Int?
)