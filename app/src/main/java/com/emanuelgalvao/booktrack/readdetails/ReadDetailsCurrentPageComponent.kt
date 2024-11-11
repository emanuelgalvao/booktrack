package com.emanuelgalvao.booktrack.readdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReadDetailsCurrentPageComponent(
    currentPage: Int,
    onSaveClick: (Int) -> Unit
) {

    val currentPageState = rememberSaveable { mutableStateOf(currentPage.toString()) }

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = currentPageState.value,
            onValueChange = { currentPageState.value = it },
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number
            ),
            leadingIcon = {
                Text(
                    text = "Página Atual:",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                )
            }
        )
        Button(
            onClick = { onSaveClick(currentPageState.value.toInt()) },
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(text = "Salvar")
        }
    }
}

@Preview
@Composable
fun ReadDetailsCurrentPageComponentPreview() {
    ReadDetailsCurrentPageComponent(
        currentPage = 140,
        onSaveClick = {  }
    )
}