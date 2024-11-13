package com.emanuelgalvao.booktrack.screens.addbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.emanuelgalvao.booktrack.screens.addbook.components.AddBookScreen
import com.emanuelgalvao.booktrack.screens.addbook.listeners.AddBookActionsListener
import com.emanuelgalvao.booktrack.shared.listeners.TopAppBarActionsListener
import com.emanuelgalvao.booktrack.ui.theme.BooktrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddBookActivity : AppCompatActivity(), TopAppBarActionsListener, AddBookActionsListener {

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
                    topAppBarActionListener = this,
                    actionsListener = this
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

    override fun onBackClick() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onSearchClick(title: String) {
        addBookViewModel.searchBooksByTitle(title)
    }

    override fun onBookSelect(bookId: String) {
        addBookViewModel.onBookSelected(bookId)
    }

    override fun onAddBookClick() {
        addBookViewModel.addBook()
    }
}