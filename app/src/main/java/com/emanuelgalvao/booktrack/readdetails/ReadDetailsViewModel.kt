package com.emanuelgalvao.booktrack.readdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanuelgalvao.booktrack.data.BookReadingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ReadDetailsViewModel(
    private val bookReadingsRepository: BookReadingsRepository
): ViewModel() {

    private val _state: MutableSharedFlow<ReadDetailsUiState> = MutableSharedFlow(replay = 1)
    val state: SharedFlow<ReadDetailsUiState> = _state.asSharedFlow()

    private val _event: MutableSharedFlow<ReadDetailsEvent?> = MutableSharedFlow()
    val event: SharedFlow<ReadDetailsEvent?> = _event

    fun loadReadData() = viewModelScope.launch(Dispatchers.IO)  {

    }

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