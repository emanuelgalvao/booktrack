package com.emanuelgalvao.booktrack.data

import com.emanuelgalvao.booktrack.shared.BookDetailsCardData

class SearchBooksRemoteDataSource(
    private val searchBooksService: SearchBooksService
): SearchBooksRepository {
    override suspend fun fetchBooksByTitle(title: String): Result<List<BookDetailsCardData>> {
        val titleReplaced = title.replace(" ", "+")
        val response = searchBooksService.searchBooks(titleReplaced)
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            } ?: run {
                return Result.failure(Exception("Ocorreu um erro ao processar a resposta."))
            }
        } else {
            return Result.failure(Exception("Ocorreu um erro na requisição."))
        }
    }
}