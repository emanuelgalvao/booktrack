package com.emanuelgalvao.booktrack.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.values.fontSizeMedium
import com.emanuelgalvao.booktrack.util.values.defaultIconSize
import com.emanuelgalvao.booktrack.util.values.spacingBig

@Composable
fun NoDataComponent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.SentimentDissatisfied,
            contentDescription = null,
            modifier = Modifier.size(defaultIconSize)
        )
        Spacer(modifier = Modifier.height(spacingBig))
        Text(
            text = stringResource(R.string.home_no_data_title),
            textAlign = TextAlign.Center,
            fontSize = fontSizeMedium
        )
        Spacer(modifier = Modifier.height(spacingBig))
        Text(
            text = stringResource(R.string.home_no_data_subtitle),
            textAlign = TextAlign.Center,
            fontSize = fontSizeMedium
        )
    }
}

@Preview
@Composable
fun NoDataComponentPreview() {
    NoDataComponent()
}