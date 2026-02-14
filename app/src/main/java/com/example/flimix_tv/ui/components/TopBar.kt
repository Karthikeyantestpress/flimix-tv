package com.example.flimix_tv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.flimix_tv.ui.theme.BackgroundDark
import com.example.flimix_tv.ui.theme.PrimaryBlue

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TopBar(
    onMoviesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(BackgroundDark)
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Flimix",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryBlue,
        )
        Spacer(modifier = Modifier.width(48.dp))
        Text(
            text = "Movies",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .focusable()
                .clickable(onClick = onMoviesClick)
                .padding(vertical = 8.dp, horizontal = 12.dp),
        )
    }
}
