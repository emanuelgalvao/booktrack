package com.emanuelgalvao.booktrack.data.datasources

import com.emanuelgalvao.booktrack.data.repositories.SearchBooksRepository
import com.emanuelgalvao.booktrack.data.services.SearchBooksService
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import com.emanuelgalvao.booktrack.util.exceptions.ProcessResponseBodyException
import com.emanuelgalvao.booktrack.util.exceptions.RequestFailureException
import com.emanuelgalvao.booktrack.util.extensions.formatToApiRequest
import javax.inject.Inject

class SearchBooksRemoteDataSource @Inject constructor(
    private val searchBooksService: SearchBooksService
): SearchBooksRepository {
    override suspend fun fetchBooksByTitle(title: String): Result<List<BookDetailsCardData>> {
        val titleFormatted = title.formatToApiRequest()
        val response = searchBooksService.searchBooks(titleFormatted)
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            } ?: run {
                return Result.failure(ProcessResponseBodyException())
            }
        } else {
            return Result.failure(RequestFailureException())
        }
    }
}