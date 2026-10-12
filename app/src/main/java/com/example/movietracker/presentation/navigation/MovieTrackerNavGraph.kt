package com.example.movietracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movietracker.feature.favorites.FavoritesScreen
import com.example.movietracker.presentation.details.ShowDetailsScreen
import com.example.movietracker.presentation.shows.ShowsScreen

sealed class Screen(val route: String) {
    data object Shows : Screen("shows")
    data object Favorites : Screen("favorites")
    data object Details : Screen("details/{showId}") {
        fun createRoute(showId: Int) = "details/$showId"
    }
}

@Composable
fun MovieTrackerNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Shows.route
    ) {
        composable(Screen.Shows.route) {
            ShowsScreen(
                onShowClick = { showId ->
                    navController.navigate(Screen.Details.createRoute(showId))
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onShowClick = { showId ->
                    navController.navigate(Screen.Details.createRoute(showId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) {
            ShowDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
