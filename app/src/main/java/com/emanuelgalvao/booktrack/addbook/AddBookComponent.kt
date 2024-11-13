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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.shared.BookDetailsCardComponent
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData
import com.emanuelgalvao.booktrack.util.extensions.isNotNull
import com.emanuelgalvao.booktrack.util.values.EMPTY_STRING
import com.emanuelgalvao.booktrack.util.values.defaultButtonHeight
import com.emanuelgalvao.booktrack.util.values.roundedButtonSize
import com.emanuelgalvao.booktrack.util.values.spacingSmall
import com.emanuelgalvao.booktrack.util.values.weightFull

@Composable
fun AddBookComponent(
    books: List<BookDetailsCardData>,
    selectedBookId: String?,
    modifier: Modifier,
    onSearchClick: (String) -> Unit,
    onBookSelect: (String) -> Unit,
    onAddBookClick: () -> Unit
) {

    val searchTextState = rememberSaveable { mutableStateOf(EMPTY_STRING) }

    Column(
        modifier = modifier
            .padding(spacingSmall)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = spacingSmall)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchTextState.value,
                onValueChange = { searchTextState.value = it },
                maxLines = 1,
                placeholder = { Text(stringResource(R.string.add_book_search_by_title_placeholder)) },
                modifier = Modifier.weight(weightFull)
            )
            Spacer(modifier = Modifier.width(spacingSmall))
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(roundedButtonSize))
                    .background(MaterialTheme.colorScheme.primary),
                onClick = {
                    onSearchClick(searchTextState.value)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(weightFull)
        ) {
            items(books) { book ->
                BookDetailsCardComponent(
                    bookDetails = book,
                    isSelected = selectedBookId == book.id,
                    modifier = Modifier
                        .padding(top = spacingSmall),
                    onClick = { onBookSelect(book.id) }
                )
            }
        }
        AnimatedVisibility(visible = selectedBookId.isNotNull()) {
            Button(
                onClick = onAddBookClick,
                modifier = Modifier
                    .padding(top = spacingSmall)
                    .fillMaxWidth()
                    .height(defaultButtonHeight)
            ) {
                Text(text = stringResource(R.string.add_book_button_text))
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