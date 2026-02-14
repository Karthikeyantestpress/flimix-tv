package com.example.flimix_tv.ui.components

import androidx.compose.foundation.border
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** TV-friendly focus modifier: adds a clear border when focused. */
fun Modifier.tvFocusBorder(
    focusedBorderWidth: Dp = 4.dp,
    focusColor: Color,
    shape: Shape,
): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    this
        .onFocusChanged { isFocused = it.isFocused }
        .border(
            width = if (isFocused) focusedBorderWidth else 0.dp,
            color = focusColor,
            shape = shape,
        )
}
