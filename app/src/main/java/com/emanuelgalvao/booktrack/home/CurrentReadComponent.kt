package com.emanuelgalvao.booktrack.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

data class CurrentReadData(
    val id: String,
    val imageUrl: String,
    val title: String,
    val author: String,
    val currentPage: String,
    val totalPages: String,
    val readProgress: Float
)

@Composable
fun CurrentReadComponent(
    currentRead: CurrentReadData,
    onClick: () -> Unit
)= with(currentRead) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
    ) {
        Text(
            text = "Leitura Atual",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Row {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator()
                    },
                    error = {

                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(140.dp)
                        .background(Color.Blue)
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = author,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            progress = readProgress
                        )
                        Text(
                            text = "$currentPage/$totalPages Páginas",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(start = 8.dp)
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
