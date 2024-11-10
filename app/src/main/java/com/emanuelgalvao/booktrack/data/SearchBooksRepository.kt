package com.emanuelgalvao.booktrack.data

import com.emanuelgalvao.booktrack.shared.BookDetailsCardData

interface SearchBooksRepository {

    suspend fun fetchBooksByTitle(title: String): Result<List<BookDetailsCardData>>
}