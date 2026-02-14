package com.example.flimix_tv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.flimix_tv.data.model.Movie
import com.example.flimix_tv.ui.components.FocusScale
import com.example.flimix_tv.ui.components.SkeletonCard
import com.example.flimix_tv.ui.components.TopBar
import com.example.flimix_tv.ui.theme.CardDark
import com.example.flimix_tv.viewmodel.UiState

private val CardRadius = 12.dp
private val GridSpacing = 28.dp
private val ContentPadding = 48.dp

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: UiState,
    onMovieClick: (Movie) -> Unit,
    onMoviesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopBar(onMoviesClick = onMoviesClick)
        when (val state = uiState) {
            is UiState.Loading -> {
                Column(modifier = Modifier.padding(horizontal = ContentPadding)) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Movies",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(GridSpacing),
                    verticalArrangement = Arrangement.spacedBy(GridSpacing),
                    contentPadding = PaddingValues(start = ContentPadding, end = ContentPadding, bottom = ContentPadding),
                ) {
                    items(count = 8) {
                        SkeletonCard()
                    }
                }
            }
            is UiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(ContentPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    )
                }
            }
            is UiState.Success -> {
                if (state.movies.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "No movies",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        )
                    }
                } else {
                    Column(modifier = Modifier.padding(horizontal = ContentPadding)) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "All Movies",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(GridSpacing),
                        verticalArrangement = Arrangement.spacedBy(GridSpacing),
                        contentPadding = PaddingValues(start = ContentPadding, end = ContentPadding, bottom = ContentPadding),
                    ) {
                        items(
                            items = state.movies,
                            key = { it.id },
                        ) { movie ->
                            MovieGridItem(
                                movie = movie,
                                onClick = { onMovieClick(movie) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun MovieGridItem(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .tvFocusScale(
                focusedScale = 1.08f,
                focusedElevation = 20.dp,
                shape = RoundedCornerShape(CardRadius),
            )
            .focusable()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(CardRadius))
                .background(CardDark),
        ) {
            AsyncImage(
                model = movie.thumbnail,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.95f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            maxLines = 2,
        )
    }
}
