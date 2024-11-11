package com.emanuelgalvao.booktrack.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.emanuelgalvao.booktrack.addbook.AddBookActivity
import com.emanuelgalvao.booktrack.data.BookReadingsLocalDataSource
import com.emanuelgalvao.booktrack.data.DatabaseBuilder
import com.emanuelgalvao.booktrack.readdetails.ReadDetailsActivity
import com.emanuelgalvao.booktrack.ui.theme.BooktrackTheme
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var addBookActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var readDetailsActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = HomeViewModel(
            bookReadingsRepository = BookReadingsLocalDataSource(
                readingBookDao = DatabaseBuilder.getInstance(this).readingBookDao()
            )
        )

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
                    onAddBookClick = {
                        homeViewModel.handleAddBookClick()
                    },
                    onTryAgainClick = {
                        homeViewModel.loadScreenData()
                    },
                    onBookClick = { bookId ->
                        homeViewModel.handleBookClick(bookId)
                    }
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
}