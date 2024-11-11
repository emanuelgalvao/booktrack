package com.emanuelgalvao.booktrack.readdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emanuelgalvao.booktrack.data.ReadingBook
import com.emanuelgalvao.booktrack.shared.BookDetailsCardComponent
import com.emanuelgalvao.booktrack.shared.BookDetailsCardData

@Composable
fun ReadDetailsComponent(
    readingBook: ReadingBook,
    modifier: Modifier,
    onCurrentPageSaveClick: (Int) -> Unit,
    onStartStopReadingClick: () -> Unit
) = with(readingBook) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        BookDetailsCardComponent(
            bookDetails = BookDetailsCardData(
                id = id,
                imageUrl = imageUrl,
                title = title,
                subtitle = subtitle,
                author = author,
                totalPages = totalPages,
                description = description
            )
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        AnimatedVisibility(visible = isReading) {
            ReadDetailsCurrentPageComponent(
                currentPage = currentPage,
                onSaveClick = onCurrentPageSaveClick
            )
        }
        Button(
            onClick = { onStartStopReadingClick() },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = if (isReading) "Parar Leitura" else "Iniciar Leitura")
        }
    }
}

@Preview
@Composable
fun ReadDetailsComponentPreview() {
    ReadDetailsComponent(
        readingBook = ReadingBook(
            id = "1",
            imageUrl = "",
            title = "Nome do Livro",
            subtitle = "Subtitulo do Livro",
            author = "Autor do livro",
            totalPages = "200",
            description = "Descrição do Livro",
            currentPage = 140,
            isReading = true
        ),
        modifier = Modifier,
        onCurrentPageSaveClick = { },
        onStartStopReadingClick = { }
    )
}