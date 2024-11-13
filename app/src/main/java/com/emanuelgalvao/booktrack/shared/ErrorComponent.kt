package com.emanuelgalvao.booktrack.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emanuelgalvao.booktrack.R
import com.emanuelgalvao.booktrack.util.extensions.isNotNull
import com.emanuelgalvao.booktrack.util.values.defaultIconSize
import com.emanuelgalvao.booktrack.util.values.spacingMedium

@Composable
fun ErrorComponent(
    messageId: Int,
    tryAgainCallback: (() -> Unit)? = null,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.SentimentDissatisfied,
            contentDescription = null,
            modifier = Modifier.size(defaultIconSize)
        )
        Text(
            text = stringResource(id = messageId),
            modifier = Modifier
                .padding(top = spacingMedium)
        )
        AnimatedVisibility(visible = tryAgainCallback.isNotNull()) {
            Button(
                modifier = Modifier.padding(top = spacingMedium),
                onClick = {
                    tryAgainCallback?.let { tryAgainCallback() }
                }
            ) {
                Text(text = stringResource(R.string.error_component_try_again_button_text))
            }
        }
    }
}

@Preview
@Composable
fun ErrorComponentWithoutTryAgainPreview() {
    ErrorComponent(
        messageId = R.string.app_name,
        modifier = Modifier
    )
}

@Preview
@Composable
fun ErrorComponentWithTryAgainPreview() {
    ErrorComponent(
        messageId = R.string.app_name,
        tryAgainCallback = {  },
        modifier = Modifier
    )
}