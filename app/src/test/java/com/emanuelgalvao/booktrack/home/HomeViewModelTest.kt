package com.emanuelgalvao.booktrack.home

import android.app.Activity
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

class HomeViewModelTest {

    private val bookReadingsRepository: BookReadingsRepository = mockk(relaxed = true)

    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun `loadScreenData should update state with DisplayReadings correctly when don't has data`() = runTest {
        coEvery { bookReadingsRepository.getCurrentRead() } returns null
        coEvery { bookReadingsRepository.getNextReadings() } returns listOf()

        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.loadScreenData().join()
        advanceUntilIdle()

        homeViewModel.state.test {
            val displayReadingsState = awaitItem() as HomeViewModel.HomeUiState.DisplayReadings
            assertEquals(null, displayReadingsState.currentReadData)
            assertEquals(0, displayReadingsState.nextReadingsListData.size)
        }
    }

    @Test
    fun `loadScreenData should update state with DisplayReadings correctly when has data`() = runTest {
        coEvery { bookReadingsRepository.getCurrentRead() } returns mockk(relaxed = true) {
            every { id } returns "id"
            every { imageUrl } returns "imageUrl"
            every { title } returns "Titulo"
            every { author } returns "Autor"
            every { currentPage } returns "100"
            every { totalPages } returns "200"
            every { readProgress } returns 0.5f
        }
        coEvery { bookReadingsRepository.getNextReadings() } returns listOf(mockk(relaxed = true))

        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.loadScreenData().join()
        advanceUntilIdle()

        homeViewModel.state.test {
            val displayReadingsState = awaitItem() as HomeViewModel.HomeUiState.DisplayReadings
            assertEquals("id", displayReadingsState.currentReadData?.id)
            assertEquals("imageUrl", displayReadingsState.currentReadData?.imageUrl)
            assertEquals("Titulo", displayReadingsState.currentReadData?.title)
            assertEquals("Autor", displayReadingsState.currentReadData?.author)
            assertEquals("100", displayReadingsState.currentReadData?.currentPage)
            assertEquals("200", displayReadingsState.currentReadData?.totalPages)
            assertEquals(0.5f, displayReadingsState.currentReadData?.readProgress)
            assertEquals(1, displayReadingsState.nextReadingsListData.size)
        }
    }

    @Test
    fun `handleDetailsResult should update event with ShowToast when result is deleted read`() = runTest {
        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.handleDetailsResult(
            result = mockk(relaxed = true) {
                every { resultCode } returns Activity.RESULT_OK
            }
        ).join()
        advanceUntilIdle()

        homeViewModel.event.test {
            val showToastEvent = awaitItem() as HomeViewModel.HomeEvent.ShowToast
            assertEquals(R.string.home_deleted_read_success, showToastEvent.messageId)
        }
    }

    @Test
    fun `handleAddBookResult should update event with ShowToast when result is added read`() = runTest {
        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.handleAddBookResult(
            result = mockk(relaxed = true) {
                every { resultCode } returns Activity.RESULT_OK
            }
        ).join()
        advanceUntilIdle()

        homeViewModel.event.test {
            val showToastEvent = awaitItem() as HomeViewModel.HomeEvent.ShowToast
            assertEquals(R.string.home_added_read_success, showToastEvent.messageId)
        }
    }

    @Test
    fun `handleAddBookResult should call loadScreenData and update state with DisplayReadings when result is added read`() = runTest {
        coEvery { bookReadingsRepository.getCurrentRead() } returns mockk(relaxed = true)
        coEvery { bookReadingsRepository.getNextReadings() } returns listOf(mockk(relaxed = true))

        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.handleAddBookResult(
            result = mockk(relaxed = true) {
                every { resultCode } returns Activity.RESULT_OK
            }
        ).join()
        advanceUntilIdle()

        homeViewModel.state.test {
            val displayReadingsEvent = awaitItem() as HomeViewModel.HomeUiState.DisplayReadings
            assertEquals(true, displayReadingsEvent != null)
        }
    }

    @Test
    fun `handleBookClick should update event with GoToDetails with correct book id`() = runTest {
        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.handleBookClick("id").join()
        advanceUntilIdle()

        homeViewModel.event.test {
            val goToReadDetailsEvent = awaitItem() as HomeViewModel.HomeEvent.GoToReadDetails
            assertEquals("id", goToReadDetailsEvent.bookId)
        }
    }

    @Test
    fun `handleAddBookClick should update event with GoToAddBook`() = runTest {
        homeViewModel = HomeViewModel(
            bookReadingsRepository = bookReadingsRepository
        )

        homeViewModel.handleAddBookClick().join()
        advanceUntilIdle()

        homeViewModel.event.test {
            val goToAddBookEvent = awaitItem() as HomeViewModel.HomeEvent.GoToAddBook
            assertEquals(true, goToAddBookEvent != null)
        }
    }
}