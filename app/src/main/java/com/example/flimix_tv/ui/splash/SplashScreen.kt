package com.example.flimix_tv.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.flimix_tv.ui.theme.BackgroundDark
import com.example.flimix_tv.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showF by remember { mutableStateOf(true) }
    val colorProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(600)
        showF = false
    }

    LaunchedEffect(showF) {
        if (!showF) {
            colorProgress.snapTo(0f)
            colorProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1200, easing = LinearEasing),
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(2600)
        onFinish()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center,
    ) {
        if (showF) {
            Text(
                text = "F",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
            )
        } else {
            val color = lerp(Color.Gray, PrimaryBlue, colorProgress.value)
            Text(
                text = "Flimix",
                style = MaterialTheme.typography.displayMedium,
                color = color,
            )
        }
    }
}
