package com.emanuelgalvao.booktrack.data.repositories

import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.screens.home.components.BookListData
import com.emanuelgalvao.booktrack.screens.home.components.CurrentReadData
import com.emanuelgalvao.booktrack.shared.components.BookDetailsCardData

interface BookReadingsRepository {

    suspend fun getReadData(bookId: String): Result<ReadingBook>

    suspend fun updateCurrentPage(bookId: String, currentPage: Int): Boolean

    suspend fun setIsReading(bookId: String, isReading: Boolean): Boolean

    suspend fun deleteReading(bookId: String): Boolean

    suspend fun addReading(book: BookDetailsCardData): Boolean

    suspend fun getCurrentRead(): CurrentReadData?

    suspend fun getNextReadings(): List<BookListData>
}