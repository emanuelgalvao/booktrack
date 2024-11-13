package com.emanuelgalvao.booktrack.screens.readdetails.components

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.values.spacingMedium
import com.emanuelgalvao.booktrack.util.values.spacingSmall

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReadDetailsCurrentPageComponent(
    currentPage: Int,
    onSaveClick: (Int) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val currentPageState = rememberSaveable { mutableStateOf(currentPage.toString()) }

    val currentPageTextFieldMaxLength = 5

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = currentPageState.value,
            onValueChange = { newValue ->
                if (newValue.length <= currentPageTextFieldMaxLength) {
                    currentPageState.value = newValue.filter { it.isDigit() }
                }
            },
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
                    text = stringResource(R.string.read_details_current_page_label),
                    modifier = Modifier
                        .padding(start = spacingMedium, end = spacingSmall)
                )
            }
        )
        Button(
            onClick = {
                onSaveClick(currentPageState.value.toInt())
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            modifier = Modifier
                .padding(start = spacingMedium)
        ) {
            Text(text = stringResource(R.string.read_details_save_button_text))
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