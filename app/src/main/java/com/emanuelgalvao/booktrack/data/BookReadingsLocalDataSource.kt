package com.emanuelgalvao.booktrack.data

import com.emanuelgalvao.booktrack.home.BookListData
import com.emanuelgalvao.booktrack.home.CurrentReadData
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData

class BookReadingsLocalDataSource(
    private val readingBookDao: ReadingBookDao
): BookReadingsRepository {
    override suspend fun getReadData(bookId: String): Result<ReadingBook> {
       val readingBook = readingBookDao.getReadingBookById(bookId)
        return readingBook?.let {
            Result.success(it)
        } ?: run {
            Result.failure(Exception("Nenhum registro encontrado com esse id."))
        }
    }

    override suspend fun updateCurrentPage(bookId: String, currentPage: Int): Boolean {
        val updatedLines = readingBookDao.setCurrentPage(bookId, currentPage)
        return updatedLines > 0
    }

    override suspend fun setIsReading(bookId: String, isReading: Boolean): Boolean {
        readingBookDao.setAllBooksAsNotIsReading()
        val updatedLines = readingBookDao.setIsReading(bookId, isReading)
        return updatedLines > 0
    }

    override suspend fun deleteReading(bookId: String): Boolean {
        val deletedLines = readingBookDao.deleteReadingBookById(bookId)
        return deletedLines > 0
    }

    override suspend fun addReading(book: BookDetailsCardData): Boolean {
        val readingBook = ReadingBook(
            id = book.id,
            imageUrl = book.imageUrl,
            title = book.title,
            subtitle = book.subtitle,
            author = book.author,
            totalPages = book.totalPages,
            description = book.description,
            isReading = false,
            currentPage = null
        )
        val insertedId = readingBookDao.insertReadingBook(readingBook)
        return insertedId > 0
    }

    override suspend fun getCurrentRead(): CurrentReadData? {
        val readingBook = readingBookDao.getReadingBookIsReading()
        return readingBook?.let {
            CurrentReadData(
                id = it.id,
                imageUrl = it.imageUrl,
                title = it.title,
                author = it.author,
                currentPage = it.currentPage?.toString() ?: "0",
                totalPages = it.totalPages,
                readProgress = (it.currentPage ?: 0) / it.totalPages.toFloat()
            )
        }
    }

    override suspend fun getNextReadings(): List<BookListData> {
        val notReadingBooks = readingBookDao.getReadingBookNotIsReading()
        return notReadingBooks.map {
            BookListData(
                id = it.id,
                imageUrl = it.imageUrl
            )
        }
    }
}