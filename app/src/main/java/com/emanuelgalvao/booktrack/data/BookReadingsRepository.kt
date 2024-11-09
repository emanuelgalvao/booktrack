package com.emanuelgalvao.booktrack.data

import com.emanuelgalvao.booktrack.readdetails.BookDetailsData

interface BookReadingsRepository {

    suspend fun getReadData(): Result<BookDetailsData>

    suspend fun updateCurrentPage(bookId: String, currentPage: Int): Boolean

}