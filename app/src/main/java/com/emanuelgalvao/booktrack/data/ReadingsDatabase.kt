package com.emanuelgalvao.booktrack.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ReadingBook::class], version = 1)
abstract class ReadingsDatabase : RoomDatabase() {
    abstract fun readingBookDao(): ReadingBookDao
}