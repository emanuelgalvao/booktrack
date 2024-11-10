package com.emanuelgalvao.booktrack.data

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var instance: ReadingsDatabase? = null

    fun getInstance(context: Context): ReadingsDatabase {
        if (instance == null) {
            synchronized(ReadingsDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReadingsDatabase::class.java,
                    "readings_database"
                ).build()
            }
        }
        return instance!!
    }
}