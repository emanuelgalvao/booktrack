package com.emanuelgalvao.booktrack.home

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanuelgalvao.booktrack.data.BookReadingsRepository
import com.emanuelgalvao.booktrack.readdetails.BookDetailsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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
    }
}