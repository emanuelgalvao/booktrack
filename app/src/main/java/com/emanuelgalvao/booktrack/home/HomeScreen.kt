package com.emanuelgalvao.booktrack.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.shared.ErrorComponent
import com.emanuelgalvao.booktrack.shared.LoadingComponent

@Composable
fun HomeScreen(
    state: HomeViewModel.HomeUiState,
    onAddBookClick: () -> Unit,
    onTryAgainClick: () -> Unit,
    onBookClick: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBookClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        when (state) {
            HomeViewModel.HomeUiState.Loading -> {
                LoadingComponent(modifier = Modifier.padding(it))
            }
            is HomeViewModel.HomeUiState.ShowError -> {
                ErrorComponent(
                    messageId = state.messageId,
                    modifier = Modifier.padding(it),
                    tryAgainCallback = onTryAgainClick
                )
            }
            is HomeViewModel.HomeUiState.DisplayReadings -> {
                HomeComponent(
                    currentReadData = state.currentReadData,
                    nextReadingsListData = state.nextReadingsListData,
                    modifier = Modifier.padding(it),
                    onBookClick = { bookId ->
                        onBookClick(bookId)
                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenLoadingStatePreview() {
    HomeScreen(
        state = HomeViewModel.HomeUiState.Loading,
        onAddBookClick = { },
        onTryAgainClick = { },
        onBookClick = { }
    )
}

@Preview
@Composable
fun HomeScreenShowErrorStatePreview() {
    HomeScreen(
        state = HomeViewModel.HomeUiState.ShowError(
            messageId = R.string.app_name
        ),
        onAddBookClick = { },
        onTryAgainClick = { },
        onBookClick = { }
    )
}

@Preview
@Composable
fun HomeScreenDisplayReadingsStatePreview() {
    HomeScreen(
        state = HomeViewModel.HomeUiState.DisplayReadings(
            currentReadData = null,
            nextReadingsListData = listOf()
        ),
        onAddBookClick = { },
        onTryAgainClick = { },
        onBookClick = { }
    )
}