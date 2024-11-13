package com.emanuelgalvao.booktrack.util.extensions

import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.home.BookListData
import com.emanuelgalvao.booktrack.home.CurrentReadData
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData

fun ReadingBook.readProgress(): Float = this.currentPage / this.totalPages.toFloat()

fun ReadingBook.toCurrentReadData(): CurrentReadData =
    CurrentReadData(
        id = this.id,
        imageUrl = this.imageUrl,
        title = this.title,
        author = this.author,
        currentPage = this.currentPage.toString(),
        totalPages = this.totalPages,
        readProgress = this.readProgress()
    )

fun ReadingBook.toBookListData(): BookListData =
    BookListData(
        id = this.id,
        imageUrl = this.imageUrl
    )

fun ReadingBook.toBookDetailsCardData(): BookDetailsCardData =
    BookDetailsCardData(
        id = this.id,
        imageUrl = this.imageUrl,
        title = this.title,
        subtitle = this.subtitle,
        author = this.author,
        totalPages = this.totalPages,
        description = this.description
    )