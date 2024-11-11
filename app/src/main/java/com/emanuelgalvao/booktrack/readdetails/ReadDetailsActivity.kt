package com.emanuelgalvao.booktrack.readdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.emanuelgalvao.booktrack.addbook.AddBookScreen
import com.emanuelgalvao.booktrack.addbook.AddBookViewModel
import com.emanuelgalvao.booktrack.data.BookReadingsLocalDataSource
import com.emanuelgalvao.booktrack.data.DatabaseBuilder
import com.emanuelgalvao.booktrack.ui.theme.BooktrackTheme
import kotlinx.coroutines.launch

class ReadDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_BOOK_ID = "bookId"
    }

    private val readDetailsViewModel: ReadDetailsViewModel = ReadDetailsViewModel(
        bookReadingsRepository = BookReadingsLocalDataSource(
            readingBookDao = DatabaseBuilder.getInstance(this).readingBookDao()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookId = intent.extras?.getString(KEY_BOOK_ID) ?: ""

        setupScreen()
        setupEventObservable()
        readDetailsViewModel.loadReadData(bookId)
    }

    private fun setupScreen() {
        setContent {
            val state = readDetailsViewModel.state.collectAsState(
                initial = ReadDetailsViewModel.ReadDetailsUiState.Loading
            )
            BooktrackTheme {
                ReadDetailsScreen(
                    state = state.value,
                    onBackClick = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onDeleteClick = {
                        readDetailsViewModel.deleteReading()
                    },
                    onCurrentPageSaveClick = { currentPage ->
                        readDetailsViewModel.updateCurrentPage(currentPage)
                    },
                    onStartStopReadingClick = {
                        readDetailsViewModel.handleChangeReadingStatus()
                    }
                )
            }
        }
    }

    private fun setupEventObservable() = lifecycleScope.launch {
        readDetailsViewModel.event.collect { event ->
            when (event) {
                is ReadDetailsViewModel.ReadDetailsEvent.ShowToast -> {
                    Toast.makeText(
                        this@ReadDetailsActivity,
                        getString(event.messageId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ReadDetailsViewModel.ReadDetailsEvent.ShowDeleted -> {
                    setResult(RESULT_OK)
                    finish()
                }
                null -> { }
            }
        }
    }
}