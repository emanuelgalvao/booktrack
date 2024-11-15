package com.emanuelgalvao.booktrack.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.values.spacingMedium

@Composable
fun LoadingComponent(
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(R.string.loading_component_label),
            modifier = Modifier
                .padding(top = spacingMedium)
        )
    }
}

@Preview
@Composable
fun LoadingComponentPreview() {
    LoadingComponent(
        modifier = Modifier
    )
}