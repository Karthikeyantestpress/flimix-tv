package com.example.flimix_tv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flimix_tv.ui.detail.DetailScreen
import com.example.flimix_tv.ui.home.HomeScreen
import com.example.flimix_tv.ui.splash.SplashScreen
import com.example.flimix_tv.viewmodel.HomeViewModel

const val ROUTE_SPLASH = "splash"
const val ROUTE_HOME = "home"
const val ROUTE_DETAIL = "detail/{movieId}"

fun NavHostController.navigateToDetail(movieId: Int) {
    navigate("detail/$movieId")
}

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
                onMovieClick = { movie -> navController.navigateToDetail(movie.id) },
                onMoviesClick = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_HOME) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(
            route = ROUTE_DETAIL,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            val movie = homeViewModel.getMovieById(movieId)
            if (movie != null) {
                DetailScreen(
                    movie = movie,
                    onPlayClick = { },
                    onMoviesClick = {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(ROUTE_HOME) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                )
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}
