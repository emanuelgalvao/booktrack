package com.emanuelgalvao.booktrack.data

import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBooksService {

    @GET("searchBooks")
    suspend fun searchBooks(
        @Query("q") query: String
    ): Response<List<BookDetailsCardData>>
}