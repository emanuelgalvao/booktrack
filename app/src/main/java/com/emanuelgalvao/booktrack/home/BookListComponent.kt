package com.emanuelgalvao.booktrack.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BookListData(
    val id: String,
    val imageUrl: String
)

@Composable
fun BookListComponent(
    bookList: List<BookListData>,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = "Próximas Leituras",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            itemsIndexed(bookList) { index, book ->
                Card(
                    modifier = Modifier
                        .padding(start = if (index != 0) 8.dp else 0.dp)
                        .width(100.dp)
                        .height(140.dp)
                        .clickable { onItemClick(book.id) }
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
fun BookListComponentPreview() {
    BookListComponent(
        bookList = listOf(
            BookListData(id = "1", imageUrl = ""),
            BookListData(id = "1", imageUrl = "")
        ),
        onItemClick = { }
    )
}