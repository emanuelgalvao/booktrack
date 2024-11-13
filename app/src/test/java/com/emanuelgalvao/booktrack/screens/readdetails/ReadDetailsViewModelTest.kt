package com.emanuelgalvao.booktrack.screens.readdetails

import app.cash.turbine.test
import com.emanuelgalvao.booktrack.data.repositories.BookReadingsRepository
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.screens.readdetails.ReadDetailsViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ReadDetailsViewModelTest {

    private val bookReadingsRepository: BookReadingsRepository = mockk(relaxed = true)

    private lateinit var readDetailsViewModel: ReadDetailsViewModel

    @Test
    fun `loadReadData method should update state with ShowError when has error on recover reading data process`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.failure(Exception(""))

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()

        readDetailsViewModel.state.test {
            val showErrorState = awaitItem() as ReadDetailsViewModel.ReadDetailsUiState.ShowError
            assertEquals(R.string.details_read_default_error, showErrorState.messageId)
        }
    }

    @Test
    fun `loadReadData method should update state with DisplayDetails when recover reading data process is successful`() = runTest {
        val readData: ReadingBook = mockk(relaxed = true) {
            every { id } returns "id"
            every { imageUrl } returns "imageUrl"
            every { title } returns "Titulo"
            every { subtitle } returns "Subtitulo"
            every { author } returns "Autor"
            every { totalPages } returns "100"
            every { description } returns "Descricao"
            every { isReading } returns true
            every { currentPage } returns 10
        }

        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(readData)

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()

        readDetailsViewModel.state.test {
            val displayDetailsState = awaitItem() as ReadDetailsViewModel.ReadDetailsUiState.DisplayDetails
            assertEquals("id", displayDetailsState.readingBook.id)
            assertEquals("imageUrl", displayDetailsState.readingBook.imageUrl)
            assertEquals("Titulo", displayDetailsState.readingBook.title)
            assertEquals("Subtitulo", displayDetailsState.readingBook.subtitle)
            assertEquals("Autor", displayDetailsState.readingBook.author)
            assertEquals("100", displayDetailsState.readingBook.totalPages)
            assertEquals("Descricao", displayDetailsState.readingBook.description)
            assertEquals(true, displayDetailsState.readingBook.isReading)
            assertEquals(10, displayDetailsState.readingBook.currentPage)
        }
    }

    @Test
    fun `updateCurrentPage method should update event with ShowToast with error message when update has error`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { totalPages } returns "100"
            }
        )
        coEvery { bookReadingsRepository.updateCurrentPage("id", 50) } returns false

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.updateCurrentPage(50).join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_update_current_page_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `updateCurrentPage method should update event with ShowToast with success message when update is successful`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { totalPages } returns "100"
            }
        )
        coEvery { bookReadingsRepository.updateCurrentPage("id", 50) } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.updateCurrentPage(50).join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_update_current_page_success, showToastEvent.messageId)
        }
    }

    @Test
    fun `updateCurrentPage method should update event with ShowToast when new current page is greater than total pages`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { totalPages } returns "200"
            }
        )
        coEvery { bookReadingsRepository.updateCurrentPage("id", 50) } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.updateCurrentPage(201).join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_update_current_page_great_than_total_pages_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `handleChangeReadingStatus method should update event with ShowToast with success message when change has error`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { isReading } returns true
            }
        )
        coEvery { bookReadingsRepository.setIsReading("id", false) } returns false

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.handleChangeReadingStatus().join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_change_is_reading_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `handleChangeReadingStatus method should update event with ShowToast with success message when change is successful`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { isReading } returns false
            }
        )
        coEvery { bookReadingsRepository.setIsReading("id", true) } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.handleChangeReadingStatus().join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_change_is_reading_success, showToastEvent.messageId)
        }
    }

    @Test
    fun `handleChangeReadingStatus method should update state with DisplayDetails to reload screen`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { isReading } returns false
            }
        )
        coEvery { bookReadingsRepository.setIsReading("id", true) } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.handleChangeReadingStatus().join()
        advanceUntilIdle()

        readDetailsViewModel.state.test {
            val displayDetailsEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsUiState.DisplayDetails
            assertEquals(true, displayDetailsEvent != null)
        }
    }

    @Test
    fun `deleteReading method should update event with ShowToast with error message when delete has error`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
            }
        )
        coEvery { bookReadingsRepository.deleteReading("id") } returns false

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.deleteReading().join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_delete_error, showToastEvent.messageId)
        }
    }

    @Test
    fun `deleteReading method should update event with ShowDeleted when delete is successful`() = runTest {
        coEvery { bookReadingsRepository.getReadData("id") } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
            }
        )
        coEvery { bookReadingsRepository.deleteReading("id") } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData("id").join()
        advanceUntilIdle()
        readDetailsViewModel.deleteReading().join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowDeleted
            assertEquals(true, showToastEvent != null)
        }
    }

}