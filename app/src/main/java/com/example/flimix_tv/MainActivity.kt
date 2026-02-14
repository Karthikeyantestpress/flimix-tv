package com.example.flimix_tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.example.flimix_tv.navigation.TvNavGraph
import com.example.flimix_tv.ui.theme.BackgroundDark
import com.example.flimix_tv.ui.theme.Flimix_tvTheme
import com.example.flimix_tv.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Flimix_tvTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundDark),
                ) {
                    val homeViewModel: HomeViewModel = viewModel()
                    TvNavGraph(homeViewModel = homeViewModel)
                }
            }
        }
    }
}
