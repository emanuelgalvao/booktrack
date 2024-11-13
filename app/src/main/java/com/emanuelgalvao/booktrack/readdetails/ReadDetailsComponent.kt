package com.emanuelgalvao.booktrack.readdetails

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.shared.BookDetailsCardComponent
import com.emanuelgalvao.booktrack.util.extensions.toBookDetailsCardData
import com.emanuelgalvao.booktrack.util.values.defaultButtonHeight
import com.emanuelgalvao.booktrack.util.values.spacingMedium
import com.emanuelgalvao.booktrack.util.values.spacingSmall
import com.emanuelgalvao.booktrack.util.values.weightFull

@Composable
fun ReadDetailsComponent(
    readingBook: ReadingBook,
    modifier: Modifier,
    onCurrentPageSaveClick: (Int) -> Unit,
    onStartStopReadingClick: () -> Unit
) = with(readingBook) {

    val descriptionMaxLines = 10

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacingSmall)
    ) {
        BookDetailsCardComponent(
            bookDetails = toBookDetailsCardData()
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify,
            maxLines = descriptionMaxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = spacingSmall)
        )
        Spacer(modifier = Modifier.weight(weightFull))
        AnimatedVisibility(visible = isReading) {
            ReadDetailsCurrentPageComponent(
                currentPage = currentPage,
                onSaveClick = onCurrentPageSaveClick
            )
        }
        Button(
            onClick = { onStartStopReadingClick() },
            modifier = Modifier
                .padding(top = spacingMedium)
                .fillMaxWidth()
                .height(defaultButtonHeight)
        ) {
            Text(text = getButtonMessage(isReading))
        }
    }
}

@Composable
private fun getButtonMessage(isReading: Boolean): String {
    return stringResource(
        if (isReading)
            R.string.details_read_stop_read_button_text
        else
            R.string.details_read_start_read_button_text
    )
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