package com.example.flimix_tv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flimix_tv.ui.home.HomeScreen
import com.example.flimix_tv.ui.splash.SplashScreen
import com.example.flimix_tv.viewmodel.HomeViewModel

const val ROUTE_SPLASH = "splash"
const val ROUTE_HOME = "home"

@Composable
fun TvNavGraph(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel,
) {
    val uiState by homeViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = ROUTE_SPLASH,
    ) {
        composable(ROUTE_SPLASH) {
            SplashScreen(
                onFinish = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
            )
        }
        composable(ROUTE_HOME) {
            HomeScreen(
                uiState = uiState,
                onMovieClick = { },
                onMoviesClick = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
