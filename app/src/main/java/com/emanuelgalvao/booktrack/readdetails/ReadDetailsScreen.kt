package com.emanuelgalvao.booktrack.readdetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.ReadingBook
import com.emanuelgalvao.booktrack.shared.ErrorComponent
import com.emanuelgalvao.booktrack.shared.LoadingComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadDetailsScreen(
    state: ReadDetailsViewModel.ReadDetailsUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Adicionar Livro")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray
                )
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
                    onCurrentPageSaveClick = {

                    },
                    onStartStopReadingClick = {

                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun ReadDetailsScreenLoadingStatePreview() {
    ReadDetailsScreen(
        state = ReadDetailsViewModel.ReadDetailsUiState.Loading
    )
}

@Preview
@Composable
fun ReadDetailsScreenShowErrorStatePreview() {
    ReadDetailsScreen(
        state = ReadDetailsViewModel.ReadDetailsUiState.ShowError(
            messageId = R.string.app_name
        )
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
                currentPage = null
            )
        )
    )
}