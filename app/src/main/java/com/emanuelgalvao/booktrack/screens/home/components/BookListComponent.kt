package com.emanuelgalvao.booktrack.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.extensions.isPositive
import com.emanuelgalvao.booktrack.util.values.fontSizeBig
import com.emanuelgalvao.booktrack.util.values.nextReadingCardHeight
import com.emanuelgalvao.booktrack.util.values.nextReadingCardWidth
import com.emanuelgalvao.booktrack.util.values.spacingBig
import com.emanuelgalvao.booktrack.util.values.spacingMedium
import com.emanuelgalvao.booktrack.util.values.spacingNone
import com.emanuelgalvao.booktrack.util.values.spacingSmall

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
    ) {
        Text(
            text = stringResource(R.string.home_next_readings_title),
            fontSize = fontSizeBig,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .padding(top = spacingSmall)
                .fillMaxWidth()
        ) {
            itemsIndexed(bookList) { index, book ->
                Card(
                    modifier = Modifier
                        .padding(start = getCardStartingPadding(index))
                        .width(nextReadingCardWidth)
                        .height(nextReadingCardHeight)
                        .clickable { onItemClick(book.id) }
                ) {
                    SubcomposeAsyncImage(
                        model = book.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(spacingMedium)
                            ) {
                                CircularProgressIndicator()
                            }
                        },
                        error = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(spacingBig)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun getCardStartingPadding(index: Int): Dp = if (index.isPositive()) spacingSmall else spacingNone

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