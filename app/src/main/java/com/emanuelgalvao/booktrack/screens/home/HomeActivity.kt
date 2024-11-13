package com.emanuelgalvao.booktrack.screens.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.emanuelgalvao.booktrack.screens.addbook.AddBookActivity
import com.emanuelgalvao.booktrack.screens.home.components.HomeScreen
import com.emanuelgalvao.booktrack.screens.home.listeners.HomeActionsListeners
import com.emanuelgalvao.booktrack.screens.readdetails.ReadDetailsActivity
import com.emanuelgalvao.booktrack.ui.theme.BooktrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeActionsListeners {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var addBookActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var readDetailsActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupScreen()
        setupEventObservable()
        setupAddBookActivityLauncher()
        setupReadDetailsActivityLauncher()
        homeViewModel.loadScreenData()
    }

    private fun setupScreen() {
        setContent {
            val state = homeViewModel.state.collectAsState(
                initial = HomeViewModel.HomeUiState.Loading
            )
            BooktrackTheme {
                HomeScreen(
                    state = state.value,
                    actionsListeners = this
                )
            }
        }
    }

    private fun setupEventObservable() = lifecycleScope.launch {
        homeViewModel.event.collect { event ->
            when (event) {
                is HomeViewModel.HomeEvent.ShowToast -> {
                    Toast.makeText(
                        this@HomeActivity,
                        getString(event.messageId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is HomeViewModel.HomeEvent.GoToReadDetails -> {
                    readDetailsActivityLauncher.launch(
                        Intent(
                            this@HomeActivity,
                            ReadDetailsActivity::class.java
                        ).apply {
                            putExtra(ReadDetailsActivity.KEY_BOOK_ID, event.bookId)
                        }
                    )
                }
                HomeViewModel.HomeEvent.GoToAddBook -> {
                    addBookActivityLauncher.launch(
                        Intent(
                            this@HomeActivity,
                            AddBookActivity::class.java
                        )
                    )
                }
                null -> { }
            }
        }
    }

    private fun setupAddBookActivityLauncher() {
        addBookActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            homeViewModel.handleAddBookResult(result)
        }
    }

    private fun setupReadDetailsActivityLauncher() {
        readDetailsActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            homeViewModel.handleDetailsResult(result)
        }
    }

    override fun onAddBookClick() {
        homeViewModel.handleAddBookClick()
    }

    override fun onTryAgainClick() {
        homeViewModel.loadScreenData()
    }

    override fun onBookClick(bookId: String) {
        homeViewModel.handleBookClick(bookId)
    }
}