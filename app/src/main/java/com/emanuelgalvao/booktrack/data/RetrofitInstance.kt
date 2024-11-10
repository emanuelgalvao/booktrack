package com.emanuelgalvao.booktrack.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://searchbooks-mwgassn6ya-uc.a.run.app/"

    val searchBooksService: SearchBooksService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchBooksService::class.java)
    }
}