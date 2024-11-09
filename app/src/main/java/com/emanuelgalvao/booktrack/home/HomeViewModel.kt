package com.emanuelgalvao.booktrack.home

import androidx.lifecycle.ViewModel
import com.emanuelgalvao.booktrack.readdetails.BookDetailsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state: StateFlow<HomeUiState> = _state

    private val _event: MutableStateFlow<HomeEvent?> = MutableStateFlow(null)
    val event: StateFlow<HomeEvent?> = _event

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