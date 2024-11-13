package com.emanuelgalvao.booktrack.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emanuelgalvao.booktrack.data.database.model.ReadingBook

@Dao
interface ReadingBookDao {

    @Query("SELECT * FROM reading_book WHERE id = :id")
    fun getReadingBookById(id: String): ReadingBook?

    @Query("UPDATE reading_book SET currentPage = :currentPage WHERE id = :bookId")
    fun setCurrentPage(bookId: String, currentPage: Int): Int

    @Query("UPDATE reading_book SET isReading = 0")
    fun setAllBooksAsNotIsReading(): Int

    @Query("UPDATE reading_book SET isReading = :isReading WHERE id = :bookId")
    fun setIsReading(bookId: String, isReading: Boolean): Int

    @Query("DELETE FROM reading_book WHERE id = :bookId")
    fun deleteReadingBookById(bookId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReadingBook(readingBook: ReadingBook): Long

    @Query("SELECT * FROM reading_book WHERE isReading = 1")
    fun getReadingBookIsReading(): ReadingBook?

    @Query("SELECT * FROM reading_book WHERE isReading = 0")
    fun getReadingBookNotIsReading(): List<ReadingBook>
}