package com.emanuelgalvao.booktrack.addbook

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.shared.CustomTopAppBar
import com.emanuelgalvao.booktrack.shared.LoadingComponent

@Composable
fun AddBookScreen(
    state: AddBookViewModel.AddBookUiState,
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onBookSelect: (String) -> Unit,
    onAddBookClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.add_book_top_bar_title),
                onBackClick = onBackClick
            )
        }
    ) {
        when (state) {
            AddBookViewModel.AddBookUiState.Loading -> {
                LoadingComponent(modifier = Modifier.padding(it))
            }
            is AddBookViewModel.AddBookUiState.DisplayBooks -> {
                AddBookComponent(
                    books = state.books,
                    selectedBookId = state.selectedBookId,
                    modifier = Modifier.padding(it),
                    onSearchClick = onSearchClick,
                    onBookSelect = { bookId ->
                        onBookSelect(bookId)
                    },
                    onAddBookClick = onAddBookClick
                )
            }
        }
    }
}

@Preview
@Composable
fun AddBookScreenLoadingStatePreview() {
    AddBookScreen(
        state = AddBookViewModel.AddBookUiState.Loading,
        onBackClick = { },
        onSearchClick = { },
        onBookSelect = { },
        onAddBookClick = { }
    )
}

@Preview
@Composable
fun AddBookScreenDisplayBooksStatePreview() {
    AddBookScreen(
        state = AddBookViewModel.AddBookUiState.DisplayBooks(
            books = listOf(),
            selectedBookId = null
        ),
        onBackClick = { },
        onSearchClick = { },
        onBookSelect = { },
        onAddBookClick = { }
    )
}