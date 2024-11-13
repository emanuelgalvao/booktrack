package com.emanuelgalvao.booktrack.screens.home

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.repositories.BookReadingsRepository
import com.emanuelgalvao.booktrack.screens.home.components.BookListData
import com.emanuelgalvao.booktrack.screens.home.components.CurrentReadData
import com.emanuelgalvao.booktrack.util.values.DEFAULT_FLOW_REPLAY_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookReadingsRepository: BookReadingsRepository
): ViewModel() {

    private val _state: MutableSharedFlow<HomeUiState> = MutableSharedFlow(replay = DEFAULT_FLOW_REPLAY_VALUE)
    val state: SharedFlow<HomeUiState> = _state.asSharedFlow()

    private val _event: MutableSharedFlow<HomeEvent?> = MutableSharedFlow(replay = DEFAULT_FLOW_REPLAY_VALUE)
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