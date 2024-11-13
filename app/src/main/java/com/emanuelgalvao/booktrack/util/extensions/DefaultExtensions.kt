package com.emanuelgalvao.booktrack.util.extensions

import com.emanuelgalvao.booktrack.util.values.PLUS_SYMBOL
import com.emanuelgalvao.booktrack.util.values.SPACE
import com.emanuelgalvao.booktrack.util.values.ZERO

fun Int.isPositive(): Boolean = this > ZERO

fun Long.isPositive(): Boolean = this > ZERO

fun String.formatToApiRequest(): String = this.replace(SPACE, PLUS_SYMBOL)

fun Any?.isNull(): Boolean = this == null

fun Any?.isNotNull(): Boolean = this != null