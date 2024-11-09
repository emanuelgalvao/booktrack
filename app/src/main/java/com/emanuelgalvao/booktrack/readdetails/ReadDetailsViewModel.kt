package com.emanuelgalvao.booktrack.readdetails

import androidx.lifecycle.ViewModel
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReadDetailsViewModel: ViewModel() {

    private val _state: MutableStateFlow<ReadDetailsUiState> = MutableStateFlow(ReadDetailsUiState.Loading)
    val state: StateFlow<ReadDetailsUiState> = _state

    private val _event: MutableStateFlow<ReadDetailsEvent?> = MutableStateFlow(null)
    val event: StateFlow<ReadDetailsEvent?> = _event

    sealed class ReadDetailsUiState {
        data object Loading: ReadDetailsUiState()
        data class ShowError(
            val messageId: Int
        ): ReadDetailsUiState()
        data class DisplayDetails(
            val bookDetailsData: BookDetailsData,
        ): ReadDetailsUiState()
    }

    sealed class ReadDetailsEvent {
        data class ShowToast(val messageId: Int): ReadDetailsEvent()
        data object ShowDeleted: ReadDetailsEvent()
    }
}