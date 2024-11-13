package com.emanuelgalvao.booktrack.data.services

import com.emanuelgalvao.booktrack.shared.components.BookDetailsCardData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBooksService {

    @GET("searchBooks")
    suspend fun searchBooks(
        @Query("q") query: String
    ): Response<List<BookDetailsCardData>>
}