package com.emanuelgalvao.booktrack.data.repositories

import com.emanuelgalvao.booktrack.shared.components.BookDetailsCardData

interface SearchBooksRepository {

    suspend fun fetchBooksByTitle(title: String): Result<List<BookDetailsCardData>>
}