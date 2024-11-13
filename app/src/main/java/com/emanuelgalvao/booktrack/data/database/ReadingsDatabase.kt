package com.emanuelgalvao.booktrack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emanuelgalvao.booktrack.data.database.dao.ReadingBookDao
import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.util.values.DATABASE_VERSION

@Database(
    entities = [ReadingBook::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class ReadingsDatabase : RoomDatabase() {
    abstract fun readingBookDao(): ReadingBookDao
}