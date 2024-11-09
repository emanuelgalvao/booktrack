package com.emanuelgalvao.booktrack.readdetails

import app.cash.turbine.test
import com.emanuelgalvao.booktrack.data.BookReadingsRepository
import com.emanuelgalvao.booktrack.R
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
        coEvery { bookReadingsRepository.getReadData() } returns Result.failure(Exception(""))

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData().join()
        advanceUntilIdle()

        readDetailsViewModel.state.test {
            val showErrorState = awaitItem() as ReadDetailsViewModel.ReadDetailsUiState.ShowError
            assertEquals(R.string.details_read_default_error, showErrorState.messageId)
        }
    }

    @Test
    fun `loadReadData method should update state with DisplayDetails when recover reading data process is successful`() = runTest {
        val readData: BookDetailsData = mockk(relaxed = true) {
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

        coEvery { bookReadingsRepository.getReadData() } returns Result.success(readData)

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData().join()
        advanceUntilIdle()

        readDetailsViewModel.state.test {
            val displayDetailsState = awaitItem() as ReadDetailsViewModel.ReadDetailsUiState.DisplayDetails
            assertEquals("id", displayDetailsState.bookDetailsData.id)
            assertEquals("imageUrl", displayDetailsState.bookDetailsData.imageUrl)
            assertEquals("Titulo", displayDetailsState.bookDetailsData.title)
            assertEquals("Subtitulo", displayDetailsState.bookDetailsData.subtitle)
            assertEquals("Autor", displayDetailsState.bookDetailsData.author)
            assertEquals("100", displayDetailsState.bookDetailsData.totalPages)
            assertEquals("Descricao", displayDetailsState.bookDetailsData.description)
            assertEquals(true, displayDetailsState.bookDetailsData.isReading)
            assertEquals(10, displayDetailsState.bookDetailsData.currentPage)
        }
    }

    @Test
    fun `updateCurrentPage method should update event with ShowToast with error message when update has error`() = runTest {
        coEvery { bookReadingsRepository.getReadData() } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
            }
        )
        coEvery { bookReadingsRepository.updateCurrentPage("id", 50) } returns false

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData().join()
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
        coEvery { bookReadingsRepository.getReadData() } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
            }
        )
        coEvery { bookReadingsRepository.updateCurrentPage("id", 50) } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData().join()
        advanceUntilIdle()
        readDetailsViewModel.updateCurrentPage(50).join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_update_current_page_success, showToastEvent.messageId)
        }
    }

    @Test
    fun `handleChangeReadingStatus method should update event with ShowToast with success message when change has error`() = runTest {
        coEvery { bookReadingsRepository.getReadData() } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { isReading } returns true
            }
        )
        coEvery { bookReadingsRepository.setIsReading("id", false) } returns false

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData().join()
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
        coEvery { bookReadingsRepository.getReadData() } returns Result.success(
            mockk(relaxed = true) {
                every { id } returns "id"
                every { isReading } returns false
            }
        )
        coEvery { bookReadingsRepository.setIsReading("id", true) } returns true

        readDetailsViewModel = ReadDetailsViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        readDetailsViewModel.loadReadData().join()
        advanceUntilIdle()
        readDetailsViewModel.handleChangeReadingStatus().join()
        advanceUntilIdle()

        readDetailsViewModel.event.test {
            val showToastEvent = awaitItem() as ReadDetailsViewModel.ReadDetailsEvent.ShowToast
            assertEquals(R.string.details_read_change_is_reading_success, showToastEvent.messageId)
        }
    }

}