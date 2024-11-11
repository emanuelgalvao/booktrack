package com.emanuelgalvao.booktrack.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

data class BookDetailsCardData(
    val id: String,
    val imageUrl: String,
    val title: String,
    val subtitle: String,
    val author: String,
    val totalPages: String,
    val description: String
)

@Composable
fun BookDetailsCardComponent(
    bookDetails: BookDetailsCardData,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit? = { }
) = with(bookDetails) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(
                border = BorderStroke(
                    if (isSelected) 1.dp else (-1).dp,
                    Color.Red
                ),
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() }
    ) {
        Row {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(16.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = 1f
                        )
                    }
                },
                error = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                Text(
                    text = "Autor: $author",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                Text(
                    text = "Páginas: $totalPages",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BookDetailsCardComponentPreview() {
    BookDetailsCardComponent(
        bookDetails = BookDetailsCardData(
            id = "1",
            imageUrl = "",
            title = "Nome do Livro",
            subtitle = "Subtitulo do Livro",
            author = "Autor do livro",
            totalPages = "200",
            description = "Descrição do Livro"
        )
    )
}