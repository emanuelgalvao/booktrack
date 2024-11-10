package com.emanuelgalvao.booktrack.addbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.SearchBooksRepository
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddBookViewModel(
    private val searchBooksRepository: SearchBooksRepository
): ViewModel() {

    private val _state: MutableSharedFlow<AddBookUiState> = MutableSharedFlow(replay = 1)
    val state: SharedFlow<AddBookUiState> = _state.asSharedFlow()

    private val _event: MutableSharedFlow<AddBookEvent?> = MutableSharedFlow(replay = 1)
    val event: SharedFlow<AddBookEvent?> = _event.asSharedFlow()

    fun searchBooksByTitle(title: String) = viewModelScope.launch(Dispatchers.IO) {
        if (title.isEmpty()) {
            _event.emit(
                AddBookEvent.ShowToast(messageId = R.string.add_book_empty_title_error)
            )
            return@launch
        }

        searchBooksRepository.fetchBooksByTitle(title).fold(
            onSuccess = {
                _state.emit(
                    AddBookUiState.DisplayBooks(
                        books = it,
                        selectedBookId = null
                    )
                )
            },
            onFailure = {
                _event.emit(
                    AddBookEvent.ShowToast(messageId = R.string.add_book_search_default_error)
                )
            }
        )
    }

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