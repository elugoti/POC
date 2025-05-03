package com.capgemini.androidpoc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capgemini.androidpoc.utils.Routes
import com.capgemini.androidpoc.presentation.screen.CountryDetailScreen
import com.capgemini.androidpoc.presentation.screen.CountryScreen
import com.capgemini.androidpoc.presentation.screen.LoginScreen
import com.capgemini.androidpoc.presentation.screen.SignupScreen

@Composable
fun CountryApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login // Start from login
    ) {
        composable(Routes.Login) { LoginScreen(navController) }
        composable(Routes.Signup) { SignupScreen(navController) }
        composable(Routes.CountryList) { CountryScreen(navController = navController) }
        composable(
            route = Routes.CountryDetail,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            CountryDetailScreen(name = name)
        }
    }
}