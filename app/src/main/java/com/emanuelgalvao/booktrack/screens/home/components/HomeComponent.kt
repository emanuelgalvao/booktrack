package com.emanuelgalvao.booktrack.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.util.extensions.isNotNull
import com.emanuelgalvao.booktrack.util.extensions.isNull
import com.emanuelgalvao.booktrack.util.values.spacingSmall

@Composable
fun HomeComponent(
    currentReadData: CurrentReadData?,
    nextReadingsListData: List<BookListData>,
    modifier: Modifier,
    onBookClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(spacingSmall)
            .fillMaxWidth()
    ) {
        AnimatedVisibility(visible = currentReadData.isNotNull()) {
            currentReadData?.let {
                CurrentReadComponent(
                    currentRead = currentReadData,
                    onClick = { onBookClick(currentReadData.id) }
                )
            }
        }
        AnimatedVisibility(visible = nextReadingsListData.isNotEmpty()) {
            BookListComponent(
                bookList = nextReadingsListData, 
                onItemClick = { bookId ->
                    onBookClick(bookId)
                }
            )
        }
        AnimatedVisibility(visible = currentReadData.isNull() && nextReadingsListData.isEmpty()) {
            NoDataComponent()
        }
    }
}

@Preview
@Composable
fun HomeComponentNoDataPreview() {
    HomeComponent(
        currentReadData = null,
        nextReadingsListData = listOf(),
        modifier = Modifier,
        onBookClick = {  }
    )
}

@Preview
@Composable
fun HomeComponentOnlyCurrentReadPreview() {
    HomeComponent(
        currentReadData = CurrentReadData(
            id = "id",
            imageUrl = "imageUrl",
            title = "Título do Livro",
            author = "Autor",
            currentPage = "140",
            totalPages = "200",
            readProgress = 0.8f
        ),
        nextReadingsListData = listOf(),
        modifier = Modifier,
        onBookClick = {  }
    )
}

@Preview
@Composable
fun HomeComponentOnlyNextReadingsPreview() {
    HomeComponent(
        currentReadData = null,
        nextReadingsListData = listOf(
            BookListData(
                id = "id",
                imageUrl = "imageUrl"
            )
        ),
        modifier = Modifier,
        onBookClick = {  }
    )
}

@Preview
@Composable
fun HomeComponentWithCurrentReadAndNextReadingsPreview() {
    HomeComponent(
        currentReadData = CurrentReadData(
            id = "id",
            imageUrl = "imageUrl",
            title = "Título do Livro",
            author = "Autor",
            currentPage = "140",
            totalPages = "200",
            readProgress = 0.8f
        ),
        nextReadingsListData = listOf(
            BookListData(
                id = "id",
                imageUrl = "imageUrl"
            )
        ),
        modifier = Modifier,
        onBookClick = {  }
    )
}