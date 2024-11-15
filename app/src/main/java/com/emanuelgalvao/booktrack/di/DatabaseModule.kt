package com.emanuelgalvao.booktrack.di

import android.content.Context
import androidx.room.Room
import com.emanuelgalvao.booktrack.data.database.dao.ReadingBookDao
import com.emanuelgalvao.booktrack.data.database.ReadingsDatabase
import com.emanuelgalvao.booktrack.util.values.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ReadingsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ReadingsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideReadingBookDao(readingsDatabase: ReadingsDatabase): ReadingBookDao {
        return readingsDatabase.readingBookDao()
    }
}