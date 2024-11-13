package com.emanuelgalvao.booktrack.screens.addbook.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.screens.addbook.listeners.AddBookActionsListener
import com.emanuelgalvao.booktrack.screens.addbook.AddBookViewModel
import com.emanuelgalvao.booktrack.shared.components.CustomTopAppBar
import com.emanuelgalvao.booktrack.shared.components.LoadingComponent
import com.emanuelgalvao.booktrack.shared.listeners.TopAppBarActionsListener

@Composable
fun AddBookScreen(
    state: AddBookViewModel.AddBookUiState,
    topAppBarActionListener: TopAppBarActionsListener,
    actionsListener: AddBookActionsListener
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.add_book_top_bar_title),
                onBackClick = { topAppBarActionListener.onBackClick() }
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
                    actionsListener = actionsListener
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
        topAppBarActionListener = object: TopAppBarActionsListener {
            override fun onBackClick() { }
        },
        actionsListener = object: AddBookActionsListener {
            override fun onSearchClick(title: String) { }
            override fun onBookSelect(bookId: String) { }
            override fun onAddBookClick() { }
        }
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
        topAppBarActionListener = object: TopAppBarActionsListener {
            override fun onBackClick() { }
        },
        actionsListener = object: AddBookActionsListener {
            override fun onSearchClick(title: String) { }
            override fun onBookSelect(bookId: String) { }
            override fun onAddBookClick() { }
        }
    )
}