package com.example.flimix_tv.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.flimix_tv.data.model.Movie
import com.example.flimix_tv.ui.components.TopBar
import com.example.flimix_tv.ui.components.tvFocusBorder
import com.example.flimix_tv.ui.theme.PrimaryBlue

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DetailScreen(
    movie: Movie,
    onPlayClick: () -> Unit,
    onMoviesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val playFocusRequester = FocusRequester()

    Column(modifier = modifier.fillMaxSize()) {
        TopBar(onMoviesClick = onMoviesClick)
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = movie.cover ?: movie.thumbnail,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.85f),
                                Color.Black
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 48.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(bottom = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = movie.year?.toString() ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f),
                        )
                        if (movie.genre.isNotEmpty()) {
                            Text(
                                text = "•",
                                color = Color.White.copy(alpha = 0.6f),
                            )
                            Text(
                                text = movie.genre.joinToString(", "),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f),
                            )
                        }
                    }
                    Text(
                        text = movie.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        maxLines = 3,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PlayNowButton(
                        focusRequester = playFocusRequester,
                        onClick = onPlayClick,
                    )
                }
            }
            LaunchedEffect(Unit) { playFocusRequester.requestFocus() }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun PlayNowButton(
    focusRequester: FocusRequester,
    onClick: () -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val backgroundColor = if (isFocused) MaterialTheme.colorScheme.primary else Color.White
    val textColor = if (isFocused) MaterialTheme.colorScheme.onPrimary else Color.Black

    Box(
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .tvFocusBorder(
                focusedBorderWidth = 4.dp,
                focusColor = PrimaryBlue,
                shape = RoundedCornerShape(8.dp),
            )
            .focusable()
            .clickable(onClick = onClick)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 32.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            "Play Now",
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
        )
    }
}
