package com.emanuelgalvao.booktrack.addbook

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
class AddBookActivity : AppCompatActivity() {

    private val addBookViewModel: AddBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupScreen()
        setupEventObservable()
        addBookViewModel.initScreen()
    }

    private fun setupScreen() {
        setContent {
            val state = addBookViewModel.state.collectAsState(
                initial = AddBookViewModel.AddBookUiState.Loading
            )
            BooktrackTheme {
                AddBookScreen(
                    state = state.value,
                    onBackClick = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onSearchClick = { title ->
                        addBookViewModel.searchBooksByTitle(title)
                    },
                    onBookSelect = { bookId ->
                        addBookViewModel.onBookSelected(bookId)
                    },
                    onAddBookClick = {
                        addBookViewModel.addBook()
                    }
                )
            }
        }
    }

    private fun setupEventObservable() = lifecycleScope.launch {
        addBookViewModel.event.collect { event ->
            when (event) {
                is AddBookViewModel.AddBookEvent.ShowToast -> {
                    Toast.makeText(
                        this@AddBookActivity,
                        getString(event.messageId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                AddBookViewModel.AddBookEvent.ShowSuccess -> {
                    setResult(RESULT_OK)
                    finish()
                }
                null -> { }
            }
        }
    }
}