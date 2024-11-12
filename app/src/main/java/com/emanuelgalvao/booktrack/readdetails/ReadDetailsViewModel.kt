package com.emanuelgalvao.booktrack.readdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.ReadingBook
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

    private val _event: MutableSharedFlow<ReadDetailsEvent?> = MutableSharedFlow(replay = 1)
    val event: SharedFlow<ReadDetailsEvent?> = _event.asSharedFlow()

    private lateinit var readingBook: ReadingBook

    fun loadReadData(bookId: String) = viewModelScope.launch(Dispatchers.IO)  {
        _state.emit(ReadDetailsUiState.Loading)
        bookReadingsRepository.getReadData(bookId).fold(
            onSuccess = {
                readingBook = it
                _state.emit(
                    ReadDetailsUiState.DisplayDetails(
                        readingBook = it
                    )
                )
            },
            onFailure = {
                _state.emit(
                    ReadDetailsUiState.ShowError(
                        messageId = R.string.details_read_default_error
                    )
                )
            }
        )
    }

    fun updateCurrentPage(currentPage: Int) = viewModelScope.launch(Dispatchers.IO) {
        val updateSuccess = bookReadingsRepository.updateCurrentPage(
            bookId = readingBook.id,
            currentPage = currentPage
        )

        val messageId = if (updateSuccess) {
            R.string.details_read_update_current_page_success
        } else {
            R.string.details_read_update_current_page_error
        }
        _event.emit(ReadDetailsEvent.ShowToast(messageId = messageId))
        loadReadData(readingBook.id)
    }

    fun handleChangeReadingStatus() = viewModelScope.launch(Dispatchers.IO) {
        val changeSuccess = bookReadingsRepository.setIsReading(
            bookId = readingBook.id,
            isReading = !readingBook.isReading
        )

        val messageId = if (changeSuccess) {
            R.string.details_read_change_is_reading_success
        } else {
            R.string.details_read_change_is_reading_error
        }
        _event.emit(ReadDetailsEvent.ShowToast(messageId = messageId))
    }

    fun deleteReading() = viewModelScope.launch(Dispatchers.IO) {
        val deleteSuccess = bookReadingsRepository.deleteReading(readingBook.id)

        if (deleteSuccess) {
            _event.emit(ReadDetailsEvent.ShowDeleted)
        } else {
            _event.emit(
                ReadDetailsEvent.ShowToast(
                    messageId = R.string.details_read_delete_error
                )
            )
        }
    }

    sealed class ReadDetailsUiState {
        data object Loading: ReadDetailsUiState()
        data class ShowError(
            val messageId: Int
        ): ReadDetailsUiState()
        data class DisplayDetails(
            val readingBook: ReadingBook,
        ): ReadDetailsUiState()
    }

    sealed class ReadDetailsEvent {
        data class ShowToast(val messageId: Int): ReadDetailsEvent()
        data object ShowDeleted: ReadDetailsEvent()
    }
}