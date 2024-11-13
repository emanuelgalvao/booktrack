package com.emanuelgalvao.booktrack.util.values

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.emanuelgalvao.booktrack.R

val spacingNone: Dp @Composable get() = dimensionResource(id = R.dimen.spacing_none)
val spacingSmall: Dp @Composable get() = dimensionResource(id = R.dimen.spacing_small)
val spacingMedium: Dp @Composable get() = dimensionResource(id = R.dimen.spacing_medium)
val spacingBig: Dp @Composable get() = dimensionResource(id = R.dimen.spacing_big)

val weightFull: Float @Composable get() = dimensionResource(id = R.dimen.weight_full).value

val defaultButtonHeight: Dp @Composable get() = dimensionResource(id = R.dimen.default_button_height)
val roundedButtonSize: Dp @Composable get() = dimensionResource(id = R.dimen.rounded_button_size)

val currentReadCardHeight: Dp @Composable get() = dimensionResource(id = R.dimen.current_read_card_height)
val currentReadCardWidth: Dp @Composable get() = dimensionResource(id = R.dimen.current_read_card_width)
val nextReadingCardHeight: Dp @Composable get() = dimensionResource(id = R.dimen.next_read_card_height)
val nextReadingCardWidth: Dp @Composable get() = dimensionResource(id = R.dimen.next_read_card_width)

val defaultIconSize: Dp @Composable get() = dimensionResource(id = R.dimen.default_icon_size)

val strokeNone: Dp @Composable get() = dimensionResource(id = R.dimen.stroke_size_none)
val strokeSmall: Dp @Composable get() = dimensionResource(id = R.dimen.stroke_size_small)

val fontSizeSmall: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.font_size_small).toSp()
    }
val fontSizeMedium: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.font_size_medium).toSp()
    }
val fontSizeBig: TextUnit
    @Composable
    get() = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.font_size_big).toSp()
    }