package com.emanuelgalvao.booktrack.screens.home.components

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
import com.emanuelgalvao.booktrack.screens.home.HomeViewModel
import com.emanuelgalvao.booktrack.screens.home.listeners.HomeActionsListeners
import com.emanuelgalvao.booktrack.shared.components.ErrorComponent
import com.emanuelgalvao.booktrack.shared.components.LoadingComponent

@Composable
fun HomeScreen(
    state: HomeViewModel.HomeUiState,
    actionsListeners: HomeActionsListeners
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { actionsListeners.onAddBookClick() },
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
                    tryAgainCallback = { actionsListeners.onTryAgainClick() }
                )
            }
            is HomeViewModel.HomeUiState.DisplayReadings -> {
                HomeComponent(
                    currentReadData = state.currentReadData,
                    nextReadingsListData = state.nextReadingsListData,
                    modifier = Modifier.padding(it),
                    onBookClick = { bookId ->
                        actionsListeners.onBookClick(bookId = bookId)
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
        actionsListeners = object: HomeActionsListeners {
            override fun onAddBookClick() { }
            override fun onTryAgainClick() { }
            override fun onBookClick(bookId: String) { }
        }
    )
}

@Preview
@Composable
fun HomeScreenShowErrorStatePreview() {
    HomeScreen(
        state = HomeViewModel.HomeUiState.ShowError(
            messageId = R.string.app_name
        ),
        actionsListeners = object: HomeActionsListeners {
            override fun onAddBookClick() { }
            override fun onTryAgainClick() { }
            override fun onBookClick(bookId: String) { }
        }
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
        actionsListeners = object: HomeActionsListeners {
            override fun onAddBookClick() { }
            override fun onTryAgainClick() { }
            override fun onBookClick(bookId: String) { }
        }
    )
}