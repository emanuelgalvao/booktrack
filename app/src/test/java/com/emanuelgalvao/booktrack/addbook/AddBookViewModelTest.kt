package com.emanuelgalvao.booktrack.addbook

import app.cash.turbine.test
import com.emanuelgalvao.booktrack.data.repositories.SearchBooksRepository
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.repositories.BookReadingsRepository
import com.emanuelgalvao.booktrack.screens.addbook.AddBookViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class AddBookViewModelTest {

    private val searchBooksRepository: SearchBooksRepository = mockk(relaxed = true)

    private val bookReadingsRepository: BookReadingsRepository = mockk(relaxed = true)

    private lateinit var addBookViewModel: AddBookViewModel

    @Test
    fun `searchBooksByTitle should update event with ShowToast when title is empty`() = runTest {
        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
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
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
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
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
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
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()

        addBookViewModel.event.test {
            val showToastEvent = awaitItem() as AddBookViewModel.AddBookEvent.ShowToast
            assertEquals(R.string.add_book_search_default_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `searchBooksByTitle should update state with DisplayBooks when request failure`() = runTest {
        coEvery { searchBooksRepository.fetchBooksByTitle("titulo") } returns Result.failure(
            Exception("")
        )

        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()

        addBookViewModel.state.test {
            val displayBooksEvent = awaitItem() as AddBookViewModel.AddBookUiState.DisplayBooks
            assertEquals(true, displayBooksEvent != null)
        }
    }

    @Test
    fun `onBookSelected should update state with new selectedBookId when new book is selected`() = runTest {
        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.onBookSelected("id1").join()
        advanceUntilIdle()

        addBookViewModel.state.test {
            assertEquals("id1", (awaitItem() as AddBookViewModel.AddBookUiState.DisplayBooks).selectedBookId)
        }
    }

    @Test
    fun `addBook should update event with ShowToast when don't have selected book`() = runTest {
        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.addBook().join()
        advanceUntilIdle()

        addBookViewModel.event.test {
            val showToastEvent = awaitItem() as AddBookViewModel.AddBookEvent.ShowToast
            assertEquals(R.string.add_book_no_selected_book, showToastEvent.messageId)
        }
    }

    @Test
    fun `addBook should update event with ShowToast when has failure on add book process`() = runTest {
        coEvery { searchBooksRepository.fetchBooksByTitle("titulo") } returns Result.success(
            listOf(
                mockk(relaxed = true) {
                    every { id } returns "id1"
                }
            )
        )
        coEvery { bookReadingsRepository.addReading(any()) } returns false

        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()
        addBookViewModel.onBookSelected("id1").join()
        advanceUntilIdle()
        addBookViewModel.addBook().join()
        advanceUntilIdle()

        addBookViewModel.event.test {
            val showToastEvent = awaitItem() as AddBookViewModel.AddBookEvent.ShowToast
            assertEquals(R.string.add_book_add_process_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `addBook should update event with ShowSuccess when add book process is successful`() = runTest {
        coEvery { searchBooksRepository.fetchBooksByTitle("titulo") } returns Result.success(
            listOf(
                mockk(relaxed = true) {
                    every { id } returns "id1"
                }
            )
        )
        coEvery { bookReadingsRepository.addReading(any()) } returns true

        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.searchBooksByTitle("titulo").join()
        advanceUntilIdle()
        addBookViewModel.onBookSelected("id1").join()
        advanceUntilIdle()
        addBookViewModel.addBook().join()
        advanceUntilIdle()

        addBookViewModel.event.test {
            assertEquals(true, (awaitItem() as AddBookViewModel.AddBookEvent.ShowSuccess != null))
        }
    }

    @Test
    fun `initScreen should update state with DisplayBooks correctly`() = runTest {
        addBookViewModel = AddBookViewModel(
            searchBooksRepository = searchBooksRepository,
            bookReadingsRepository = bookReadingsRepository
        )

        addBookViewModel.initScreen().join()
        advanceUntilIdle()

        addBookViewModel.state.test {
            val displayBooksState = awaitItem() as AddBookViewModel.AddBookUiState.DisplayBooks
            assertEquals(0, displayBooksState.books.size)
            assertEquals(null, displayBooksState.selectedBookId)
        }
    }

}