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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.flimix_tv.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay

private val SplashViolet = Color(0xFF7C3AED)
private val SplashCyan = Color(0xFF06B6D4)
private val SplashPink = Color(0xFFEC4899)
private val SplashTeal = Color(0xFF14B8A6)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showLogo by remember { mutableStateOf(true) }
    val colorProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(500)
        showLogo = false
    }

    LaunchedEffect(showLogo) {
        if (!showLogo) {
            colorProgress.snapTo(0f)
            colorProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(2400)
        onFinish()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryBlue,
                        SplashViolet,
                        SplashPink,
                        SplashCyan,
                        SplashTeal,
                        PrimaryBlue
                    ),
                    startY = 0f,
                    endY = 2000f
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (showLogo) {
            Text(
                text = "F",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White,
            )
        } else {
            val color = lerp(Color.White.copy(alpha = 0.9f), Color.White, colorProgress.value)
            Text(
                text = "Flimix",
                style = MaterialTheme.typography.displayMedium,
                color = color,
            )
        }
    }
}
