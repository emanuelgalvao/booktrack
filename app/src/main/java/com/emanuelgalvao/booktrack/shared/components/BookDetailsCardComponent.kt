package com.emanuelgalvao.booktrack.shared.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.values.currentReadCardHeight
import com.emanuelgalvao.booktrack.util.values.currentReadCardWidth
import com.emanuelgalvao.booktrack.util.values.fontSizeMedium
import com.emanuelgalvao.booktrack.util.values.fontSizeSmall
import com.emanuelgalvao.booktrack.util.values.spacingMedium
import com.emanuelgalvao.booktrack.util.values.spacingSmall
import com.emanuelgalvao.booktrack.util.values.strokeNone
import com.emanuelgalvao.booktrack.util.values.strokeSmall

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

    val titleSubtitleMaxLines = 2

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(currentReadCardHeight)
            .border(
                border = BorderStroke(
                    getStrokeSize(isSelected = isSelected),
                    MaterialTheme.colorScheme.primary
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
                            .size(spacingMedium)
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(spacingMedium)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(currentReadCardWidth)
            )
            Column(
                modifier = Modifier
                    .padding(spacingSmall)
            ) {
                Text(
                    text = title,
                    fontSize = fontSizeMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = titleSubtitleMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = fontSizeSmall,
                    maxLines = titleSubtitleMaxLines,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = spacingSmall)
                )
                Text(
                    text = stringResource(R.string.read_details_author_label, author),
                    fontSize = fontSizeSmall,
                    modifier = Modifier
                        .padding(top = spacingSmall)
                )
                Text(
                    text = stringResource(R.string.read_details_pages_label, totalPages),
                    fontSize = fontSizeSmall,
                    modifier = Modifier
                        .padding(top = spacingSmall)
                )
            }
        }
    }
}

@Composable
private fun getStrokeSize(isSelected: Boolean) = if (isSelected) strokeSmall else strokeNone

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