package com.emanuelgalvao.booktrack.readdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.emanuelgalvao.booktrack.ui.theme.BooktrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_BOOK_ID = "bookId"
    }

    private val readDetailsViewModel: ReadDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookId = intent.extras?.getString(KEY_BOOK_ID).orEmpty()

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