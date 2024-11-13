package com.emanuelgalvao.booktrack.data.datasources

import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.data.database.dao.ReadingBookDao
import com.emanuelgalvao.booktrack.data.repositories.BookReadingsRepository
import com.emanuelgalvao.booktrack.screens.home.components.BookListData
import com.emanuelgalvao.booktrack.screens.home.components.CurrentReadData
import com.emanuelgalvao.booktrack.shared.components.BookDetailsCardData
import com.emanuelgalvao.booktrack.util.exceptions.RegisterNotFoundException
import com.emanuelgalvao.booktrack.util.extensions.isPositive
import com.emanuelgalvao.booktrack.util.extensions.toBookListData
import com.emanuelgalvao.booktrack.util.extensions.toCurrentReadData
import com.emanuelgalvao.booktrack.util.extensions.toReadingBook
import javax.inject.Inject

class BookReadingsLocalDataSource @Inject constructor(
    private val readingBookDao: ReadingBookDao
): BookReadingsRepository {
    override suspend fun getReadData(bookId: String): Result<ReadingBook> {
       val readingBook = readingBookDao.getReadingBookById(bookId)
        return readingBook?.let {
            Result.success(it)
        } ?: run {
            Result.failure(RegisterNotFoundException())
        }
    }

    override suspend fun updateCurrentPage(bookId: String, currentPage: Int): Boolean {
        val updatedLines = readingBookDao.setCurrentPage(bookId, currentPage)
        return updatedLines.isPositive()
    }

    override suspend fun setIsReading(bookId: String, isReading: Boolean): Boolean {
        readingBookDao.setAllBooksAsNotIsReading()
        val updatedLines = readingBookDao.setIsReading(bookId, isReading)
        return updatedLines.isPositive()
    }

    override suspend fun deleteReading(bookId: String): Boolean {
        val deletedLines = readingBookDao.deleteReadingBookById(bookId)
        return deletedLines.isPositive()
    }

    override suspend fun addReading(book: BookDetailsCardData): Boolean {
        val readingBook = book.toReadingBook()
        val insertedId = readingBookDao.insertReadingBook(readingBook)
        return insertedId.isPositive()
    }

    override suspend fun getCurrentRead(): CurrentReadData? {
        val readingBook = readingBookDao.getReadingBookIsReading()
        return readingBook?.toCurrentReadData()
    }

    override suspend fun getNextReadings(): List<BookListData> {
        val notReadingBooks = readingBookDao.getReadingBookNotIsReading()
        return notReadingBooks.map {
            it.toBookListData()
        }
    }
}