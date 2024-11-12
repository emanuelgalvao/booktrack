package com.emanuelgalvao.booktrack.home

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.BookReadingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bookReadingsRepository: BookReadingsRepository
): ViewModel() {

    private val _state: MutableSharedFlow<HomeUiState> = MutableSharedFlow(replay = 1)
    val state: SharedFlow<HomeUiState> = _state.asSharedFlow()

    private val _event: MutableSharedFlow<HomeEvent?> = MutableSharedFlow(replay = 1)
    val event: SharedFlow<HomeEvent?> = _event.asSharedFlow()

    fun loadScreenData() = viewModelScope.launch(Dispatchers.IO) {
        _state.emit(
            HomeUiState.Loading
        )
        val currentReadData = bookReadingsRepository.getCurrentRead()
        val nextReadingsListData = bookReadingsRepository.getNextReadings()

        _state.emit(
            HomeUiState.DisplayReadings(
                currentReadData = currentReadData,
                nextReadingsListData = nextReadingsListData
            )
        )
    }

    fun handleDetailsResult(result: ActivityResult) = viewModelScope.launch(Dispatchers.IO) {
        if (result.resultCode == Activity.RESULT_OK) {
            _event.emit(
                HomeEvent.ShowToast(
                    messageId = R.string.home_deleted_read_success
                )
            )
        }
        loadScreenData()
    }

    fun handleAddBookResult(result: ActivityResult) = viewModelScope.launch(Dispatchers.IO) {
        if (result.resultCode == Activity.RESULT_OK) {
            _event.emit(
                HomeEvent.ShowToast(
                    messageId = R.string.home_added_read_success
                )
            )
            loadScreenData()
        }
    }

    fun handleBookClick(bookId: String) = viewModelScope.launch(Dispatchers.IO) {
        _event.emit(
            HomeEvent.GoToReadDetails(
                bookId = bookId
            )
        )
    }

    fun handleAddBookClick() = viewModelScope.launch(Dispatchers.IO) {
        _event.emit(
            HomeEvent.GoToAddBook
        )
    }

    sealed class HomeUiState {
        data object Loading: HomeUiState()
        data class ShowError(
            val messageId: Int
        ): HomeUiState()
        data class DisplayReadings(
            val currentReadData: CurrentReadData?,
            val nextReadingsListData: List<BookListData>
        ): HomeUiState()
    }

    sealed class HomeEvent {
        data class ShowToast(val messageId: Int): HomeEvent()
        data class GoToReadDetails(val bookId: String): HomeEvent()
        data object GoToAddBook: HomeEvent()
    }
}