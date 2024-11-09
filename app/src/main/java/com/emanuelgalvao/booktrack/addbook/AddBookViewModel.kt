package com.emanuelgalvao.booktrack.addbook

import androidx.lifecycle.ViewModel
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddBookViewModel: ViewModel() {

    private val _state: MutableStateFlow<AddBookUiState> = MutableStateFlow(AddBookUiState.Loading)
    val state: StateFlow<AddBookUiState> = _state

    private val _event: MutableStateFlow<AddBookEvent?> = MutableStateFlow(null)
    val event: StateFlow<AddBookEvent?> = _event

    sealed class AddBookUiState {
        data object Loading: AddBookUiState()
        data class DisplayBooks(
            val books: List<BookDetailsCardData>,
            val selectedBookId: String?
        ): AddBookUiState()
    }

    sealed class AddBookEvent {
        data class ShowToast(val messageId: Int): AddBookEvent()
        data object ShowSuccess: AddBookEvent()
    }
}