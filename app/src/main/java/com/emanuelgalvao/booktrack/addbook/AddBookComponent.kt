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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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

@Composable
fun AddBookComponent(
    books: List<BookDetailsCardData>,
    selectedBookId: String?,
    modifier: Modifier,
    onSearchClick: (String) -> Unit,
    onBookSelect: (String) -> Unit,
    onAddBookClick: () -> Unit
) {

    val searchTextState = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchTextState.value,
                onValueChange = { searchTextState.value = it },
                maxLines = 1,
                placeholder = { Text("Pesquisar livro por título...") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.Red),
                onClick = {
                    onSearchClick(searchTextState.value)
                }
            ) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            }
        }
        AnimatedVisibility(visible = books.isEmpty()) {
            ErrorComponent(
                messageId = R.string.app_name,
                modifier = Modifier
                    .weight(1f)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(books) { book ->
                BookDetailsCardComponent(
                    bookDetails = book,
                    isSelected = selectedBookId == book.id,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    onClick = { onBookSelect(book.id) }
                )
            }
        }
        AnimatedVisibility(visible = selectedBookId != null) {
            Button(
                onClick = onAddBookClick,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "Adicionar Livro")
            }
        }
    }
}

@Preview
@Composable
fun AddBookComponentEmptyListPreview() {
    AddBookComponent(
        books = listOf(),
        selectedBookId = null,
        modifier = Modifier,
        onSearchClick = { },
        onBookSelect = { },
        onAddBookClick = { }
    )
}

@Preview
@Composable
fun AddBookComponentWithBookDataWithoutSelectedBookPreview() {
    AddBookComponent(
        books = listOf(
            BookDetailsCardData(
                id = "id1",
                imageUrl = "imageUrl",
                title = "Título do Livro",
                subtitle = "Subtítulo",
                author = "Autor do Livro",
                totalPages = "200",
                description = "Descrição do Livro"
            ),
            BookDetailsCardData(
                id = "id2",
                imageUrl = "imageUrl",
                title = "Título do Livro",
                subtitle = "Subtítulo",
                author = "Autor do Livro",
                totalPages = "200",
                description = "Descrição do Livro"
            )
        ),
        selectedBookId = null,
        modifier = Modifier,
        onSearchClick = { },
        onBookSelect = { },
        onAddBookClick = { }
    )
}

@Preview
@Composable
fun AAddBookComponentWithBookDataWithSelectedBookPreview() {
    AddBookComponent(
        books = listOf(
            BookDetailsCardData(
                id = "id1",
                imageUrl = "imageUrl",
                title = "Título do Livro",
                subtitle = "Subtítulo",
                author = "Autor do Livro",
                totalPages = "200",
                description = "Descrição do Livro"
            ),
            BookDetailsCardData(
                id = "id2",
                imageUrl = "imageUrl",
                title = "Título do Livro",
                subtitle = "Subtítulo",
                author = "Autor do Livro",
                totalPages = "200",
                description = "Descrição do Livro"
            )
        ),
        selectedBookId = "id2",
        modifier = Modifier,
        onSearchClick = { },
        onBookSelect = { },
        onAddBookClick = { }
    )
}