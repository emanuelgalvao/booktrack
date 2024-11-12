package com.emanuelgalvao.booktrack.di

import com.emanuelgalvao.booktrack.data.SearchBooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://searchbooks-mwgassn6ya-uc.a.run.app/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchBooksService(retrofit: Retrofit): SearchBooksService {
        return retrofit.create(SearchBooksService::class.java)
    }
}