package com.emanuelgalvao.booktrack.data

import com.emanuelgalvao.booktrack.readdetails.BookDetailsData
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData

interface BookReadingsRepository {

    suspend fun getReadData(): Result<BookDetailsData>

    suspend fun updateCurrentPage(bookId: String, currentPage: Int): Boolean

    suspend fun setIsReading(bookId: String, isReading: Boolean): Boolean

    suspend fun deleteReading(bookId: String): Boolean

    suspend fun addReading(book: BookDetailsCardData): Boolean
}