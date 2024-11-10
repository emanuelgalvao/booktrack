package com.emanuelgalvao.booktrack.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface ReadingBookDao {

    @Query("SELECT * FROM reading_book WHERE id = :id")
    suspend fun getReadingBookById(id: String): ReadingBook?

    @Query("UPDATE reading_book SET currentPage = :currentPage WHERE id = :bookId")
    suspend fun setCurrentPage(bookId: String, currentPage: Int): Int

    @Query("UPDATE reading_book SET isReading = 0")
    suspend fun setAllBooksAsNotIsReading(): Int

    @Query("UPDATE reading_book SET isReading = :isReading WHERE id = :bookId")
    suspend fun setIsReading(bookId: String, isReading: Boolean): Int

    @Query("DELETE FROM reading_book WHERE id = :bookId")
    suspend fun deleteReadingBookById(bookId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadingBook(readingBook: ReadingBook): Long

    @Query("SELECT * FROM reading_book WHERE isReading = 1")
    suspend fun getReadingBookIsReading(): ReadingBook?

    @Query("SELECT * FROM reading_book WHERE isReading = 0")
    suspend fun getReadingBookNotIsReading(): List<ReadingBook>
}