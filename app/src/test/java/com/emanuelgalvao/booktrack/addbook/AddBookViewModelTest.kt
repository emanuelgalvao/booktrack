package com.emanuelgalvao.booktrack.addbook

import app.cash.turbine.test
import com.emanuelgalvao.booktrack.data.SearchBooksRepository
import com.emanuelgalvao.booktrack.R
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class AddBookViewModelTest {

    private val searchBooksRepository: SearchBooksRepository = mockk(relaxed = true)

    private lateinit var addBookViewModel: AddBookViewModel

    @Test
    fun `searchBooksByTitle should update event with ShowToast when title is empty`() = runTest {
        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository
        )

        addBookViewModel.searchBooksByTitle(title = "").join()
        advanceUntilIdle()

        addBookViewModel.event.test {
            val showToastEvent = awaitItem() as AddBookViewModel.AddBookEvent.ShowToast
            assertEquals(R.string.add_book_empty_title_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `searchBooksByTitle should update state with DisplayBooks with books when request is successful with data`() = runTest {
        coEvery { searchBooksRepository.fetchBooksByTitle("titulo") } returns Result.success(
            listOf(
                mockk(relaxed = true)
            )
        )

        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()

        addBookViewModel.state.test {
            val displayBooksState = awaitItem() as AddBookViewModel.AddBookUiState.DisplayBooks
            assertEquals(1, displayBooksState.books.size)
            assertEquals(null, displayBooksState.selectedBookId)
        }
    }

    @Test
    fun `searchBooksByTitle should update state with DisplayBooks with empty list when request is successful without data`() = runTest {
        coEvery { searchBooksRepository.fetchBooksByTitle("titulo") } returns Result.success(
            listOf()
        )

        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()

        addBookViewModel.state.test {
            val displayBooksState = awaitItem() as AddBookViewModel.AddBookUiState.DisplayBooks
            assertEquals(0, displayBooksState.books.size)
            assertEquals(null, displayBooksState.selectedBookId)
        }
    }

    @Test
    fun `searchBooksByTitle should update event with ShowToast when request failure`() = runTest {
        coEvery { searchBooksRepository.fetchBooksByTitle("titulo") } returns Result.failure(
            Exception("")
        )

        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()

        addBookViewModel.event.test {
            val showToastEvent = awaitItem() as AddBookViewModel.AddBookEvent.ShowToast
            assertEquals(R.string.add_book_search_default_error, showToastEvent.messageId)
        }
    }

}