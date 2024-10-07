package com.example.githubclient.feature_app.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubclient.feature_app.presentation.downloads.DownloadsScreen
import com.example.githubclient.feature_app.presentation.search.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Search) {

        composable<Screen.Search> {
            SearchScreen(navController = navController)
        }

        composable<Screen.Downloads> {
            DownloadsScreen(navController = navController)
        }

    }

}