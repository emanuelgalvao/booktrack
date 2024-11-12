package com.emanuelgalvao.booktrack.di

import com.emanuelgalvao.booktrack.data.BookReadingsLocalDataSource
import com.emanuelgalvao.booktrack.data.BookReadingsRepository
import com.emanuelgalvao.booktrack.data.ReadingBookDao
import com.emanuelgalvao.booktrack.data.SearchBooksRemoteDataSource
import com.emanuelgalvao.booktrack.data.SearchBooksRepository
import com.emanuelgalvao.booktrack.data.SearchBooksService
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