package com.emanuelgalvao.booktrack.di

import com.emanuelgalvao.booktrack.data.datasources.BookReadingsLocalDataSource
import com.emanuelgalvao.booktrack.data.repositories.BookReadingsRepository
import com.emanuelgalvao.booktrack.data.database.dao.ReadingBookDao
import com.emanuelgalvao.booktrack.data.datasources.SearchBooksRemoteDataSource
import com.emanuelgalvao.booktrack.data.repositories.SearchBooksRepository
import com.emanuelgalvao.booktrack.data.services.SearchBooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookReadingsRepository(readingBookDao: ReadingBookDao): BookReadingsRepository {
        return BookReadingsLocalDataSource(
            readingBookDao = readingBookDao
        )
    }

    @Singleton
    @Provides
    fun provideSearchBooksRepository(searchBooksService: SearchBooksService): SearchBooksRepository {
        return SearchBooksRemoteDataSource(
            searchBooksService = searchBooksService
        )
    }
}