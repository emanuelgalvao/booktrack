package com.emanuelgalvao.booktrack.util.extensions

import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.shared.components.BookDetailsCardData
import com.emanuelgalvao.booktrack.util.values.ZERO

fun BookDetailsCardData.toReadingBook(): ReadingBook =
    ReadingBook(
        id = this.id,
        imageUrl = this.imageUrl,
        title = this.title,
        subtitle = this.subtitle,
        author = this.author,
        totalPages = this.totalPages,
        description = this.description,
        isReading = false,
        currentPage = ZERO
    )