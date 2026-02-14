package com.example.flimix_tv.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

/**
 * OTT-style focus: scale up, elevation shadow, and z-index so focused item "pops" in front (e.g. Hotstar).
 * No border; use for cards and large focus targets.
 */
fun Modifier.tvFocusScale(
    focusedScale: Float = 1.08f,
    focusedElevation: Dp = 16.dp,
    shape: Shape,
): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) focusedScale else 1f,
        animationSpec = tween(durationMillis = 150)
    )
    val density = LocalDensity.current
    val elevationPx = with(density) { focusedElevation.toPx() }
    val elevationValue by animateFloatAsState(
        targetValue = if (isFocused) elevationPx else 0f,
        animationSpec = tween(durationMillis = 150)
    )
    this
        .onFocusChanged { isFocused = it.isFocused }
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            shadowElevation = elevationValue
        }
        .zIndex(if (isFocused) 1f else 0f)
}
