package com.emanuelgalvao.booktrack.screens.readdetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.screens.readdetails.ReadDetailsViewModel
import com.emanuelgalvao.booktrack.screens.readdetails.listeners.ReadDetailsActionsListener
import com.emanuelgalvao.booktrack.shared.components.CustomTopAppBar
import com.emanuelgalvao.booktrack.shared.components.ErrorComponent
import com.emanuelgalvao.booktrack.shared.components.LoadingComponent
import com.emanuelgalvao.booktrack.shared.listeners.TopAppBarActionsListener

@Composable
fun ReadDetailsScreen(
    state: ReadDetailsViewModel.ReadDetailsUiState,
    topAppBarActionsListener: TopAppBarActionsListener,
    actionsListener: ReadDetailsActionsListener
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.read_details_top_bar_title),
                onBackClick = {  topAppBarActionsListener.onBackClick() },
                actions = {
                    IconButton(
                        onClick = { actionsListener.onDeleteClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        when (state) {
            ReadDetailsViewModel.ReadDetailsUiState.Loading -> {
                LoadingComponent(modifier = Modifier.padding(it))
            }
            is ReadDetailsViewModel.ReadDetailsUiState.ShowError -> {
                ErrorComponent(
                    messageId = state.messageId,
                    tryAgainCallback = {

                    },
                    modifier = Modifier.padding(it)
                )
            }
            is ReadDetailsViewModel.ReadDetailsUiState.DisplayDetails -> {
                ReadDetailsComponent(
                    readingBook = state.readingBook,
                    modifier = Modifier.padding(it),
                    onCurrentPageSaveClick = { currentPage ->
                        actionsListener.onCurrentPageSaveClick(currentPage)
                    },
                    onStartStopReadingClick = { actionsListener.onStartStopReadingClick() }
                )
            }

        }
    }
}

@Preview
@Composable
fun ReadDetailsScreenLoadingStatePreview() {
    ReadDetailsScreen(
        state = ReadDetailsViewModel.ReadDetailsUiState.Loading,
        topAppBarActionsListener = object: TopAppBarActionsListener {
            override fun onBackClick() { }
        },
        actionsListener = object: ReadDetailsActionsListener {
            override fun onDeleteClick() { }
            override fun onCurrentPageSaveClick(currentPage: Int) { }
            override fun onStartStopReadingClick() { }
        }
    )
}

@Preview
@Composable
fun ReadDetailsScreenShowErrorStatePreview() {
    ReadDetailsScreen(
        state = ReadDetailsViewModel.ReadDetailsUiState.ShowError(
            messageId = R.string.app_name
        ),
        topAppBarActionsListener = object: TopAppBarActionsListener {
            override fun onBackClick() { }
        },
        actionsListener = object: ReadDetailsActionsListener {
            override fun onDeleteClick() { }
            override fun onCurrentPageSaveClick(currentPage: Int) { }
            override fun onStartStopReadingClick() { }
        }
    )
}

@Preview
@Composable
fun ReadDetailsScreenDisplayDetailsStatePreview() {
    ReadDetailsScreen(
        state = ReadDetailsViewModel.ReadDetailsUiState.DisplayDetails(
            readingBook = ReadingBook(
                id = "id1",
                imageUrl = "imageUrl",
                title = "Título do Livro",
                subtitle = "Subtítulo",
                author = "Autor do Livro",
                totalPages = "200",
                description = "Descrição do Livro",
                isReading = false,
                currentPage = 0
            )
        ),
        topAppBarActionsListener = object: TopAppBarActionsListener {
            override fun onBackClick() { }
        },
        actionsListener = object: ReadDetailsActionsListener {
            override fun onDeleteClick() { }
            override fun onCurrentPageSaveClick(currentPage: Int) { }
            override fun onStartStopReadingClick() { }
        }
    )
}