package com.emanuelgalvao.booktrack.addbook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.shared.BookDetailsCardComponent
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import com.emanuelgalvao.booktrack.shared.ErrorComponent
import com.emanuelgalvao.booktrack.shared.LoadingComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    state: AddBookViewModel.AddBookUiState
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
            AddBookViewModel.AddBookUiState.Loading -> {
                LoadingComponent(modifier = Modifier.padding(it))
            }
            is AddBookViewModel.AddBookUiState.DisplayBooks -> {
                AddBookComponent(
                    books = state.books,
                    selectedBookId = state.selectedBookId,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

@Preview
@Composable
fun AddBookScreenLoadingStatePreview() {
    AddBookScreen(
        state = AddBookViewModel.AddBookUiState.Loading
    )
}

@Preview
@Composable
fun AddBookScreenDisplayBooksStatePreview() {
    AddBookScreen(
        state = AddBookViewModel.AddBookUiState.DisplayBooks(
            books = listOf(),
            selectedBookId = null
        )
    )
}