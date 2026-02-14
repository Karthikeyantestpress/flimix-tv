package com.example.flimix_tv.ui.player

import android.view.KeyEvent
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.flimix_tv.ui.components.tvFocusBorder
import com.example.flimix_tv.ui.theme.PrimaryBlue

private const val SEEK_MS = 10_000L

@UnstableApi
@Composable
fun PlayerScreen(
    playUrl: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var controllerVisible by remember { mutableStateOf(true) }
    val playFocusRequester = remember { FocusRequester() }

    BackHandler {
        if (controllerVisible) {
            controllerVisible = false
        } else {
            onBack()
        }
    }

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    LaunchedEffect(playUrl) {
        player.setMediaItem(MediaItem.fromUri(playUrl))
        player.prepare()
        player.playWhenReady = true
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    LaunchedEffect(controllerVisible) {
        if (controllerVisible) {
            playFocusRequester.requestFocus()
        }
    }

    var isPlaying by remember { mutableStateOf(player.playWhenReady) }
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        player.addListener(listener)
        onDispose { player.removeListener(listener) }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_DOWN &&
                    keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DPAD_DOWN &&
                    !controllerVisible
                ) {
                    controllerVisible = true
                    true
                } else {
                    false
                }
            },
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        if (controllerVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.85f),
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY,
                        )
                    )
                    .padding(48.dp),
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 48.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    PlayerControlButton(
                        label = "−10s",
                        focusRequester = playFocusRequester,
                        onClick = {
                            player.seekTo((player.currentPosition - SEEK_MS).coerceAtLeast(0))
                        },
                    )
                    PlayerControlButton(
                        label = if (isPlaying) "⏸ Pause" else "▶ Play",
                        onClick = {
                            if (player.isPlaying) player.pause() else player.play()
                        },
                    )
                    PlayerControlButton(
                        label = "+10s",
                        onClick = {
                            player.seekTo(
                                (player.currentPosition + SEEK_MS).coerceAtMost(player.duration.coerceAtLeast(0))
                            )
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun PlayerControlButton(
    label: String,
    onClick: () -> Unit,
    focusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier,
) {
    val modifierWithFocus = if (focusRequester != null) {
        modifier.focusRequester(focusRequester)
    } else {
        modifier
    }
    Box(
        modifier = modifierWithFocus
            .padding(horizontal = 16.dp)
            .tvFocusBorder(
                focusedBorderWidth = 4.dp,
                focusColor = PrimaryBlue,
                shape = CircleShape,
            )
            .focusable()
            .clickable(onClick = onClick)
            .size(72.dp)
            .background(Color.Black.copy(alpha = 0.7f), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
    }
}
