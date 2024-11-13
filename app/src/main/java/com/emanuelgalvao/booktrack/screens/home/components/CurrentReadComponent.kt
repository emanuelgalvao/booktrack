package com.emanuelgalvao.booktrack.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.values.currentReadCardHeight
import com.emanuelgalvao.booktrack.util.values.currentReadCardWidth
import com.emanuelgalvao.booktrack.util.values.fontSizeBig
import com.emanuelgalvao.booktrack.util.values.fontSizeMedium
import com.emanuelgalvao.booktrack.util.values.fontSizeSmall
import com.emanuelgalvao.booktrack.util.values.spacingSmall
import com.emanuelgalvao.booktrack.util.values.weightFull

data class CurrentReadData(
    val id: String,
    val imageUrl: String,
    val title: String,
    val author: String,
    val currentPage: String,
    val totalPages: String,
    val readProgress: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentReadComponent(
    currentRead: CurrentReadData,
    onClick: () -> Unit
)= with(currentRead) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.home_current_read_title),
            fontSize = fontSizeBig,
            fontWeight = FontWeight.Bold
        )
        Card(
            onClick = onClick,
            modifier = Modifier
                .padding(top = spacingSmall)
                .fillMaxWidth()
                .height(currentReadCardHeight)
        ) {
            Row {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator()
                    },
                    error = {

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
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = author,
                        fontSize = fontSizeSmall,
                        modifier = Modifier
                            .padding(top = spacingSmall)
                    )
                    Spacer(modifier = Modifier.weight(weightFull))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            progress = readProgress,
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "$currentPage/$totalPages Páginas",
                            fontSize = fontSizeSmall,
                            modifier = Modifier
                                .padding(start = spacingSmall)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrentReadComponentPreview() {
    CurrentReadComponent(
        currentRead = CurrentReadData(
            id = "1",
            imageUrl = "",
            title = "Nome do Livro",
            author = "Nome do Autor",
            currentPage = "140",
            totalPages = "200",
            readProgress = 0.7f
        ),
        onClick = { }
    )
}
