package com.example.appsinfo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appsinfo.ui.appInformation.AppInformationScreen
import com.example.appsinfo.ui.appsList.AppsListScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.AppsListScreen.route
    ) {

        composable(route = Screens.AppsListScreen.route) {
            AppsListScreen(
                onInfoClicked = { packageName ->
                    navController.navigate(
                        route = Screens.AppInformationScreen.route.replace(
                            "{packageName}",
                            packageName
                        )
                    )
                }
            )
        }

        composable(
            route = Screens.AppInformationScreen.route,
        ) {
            val packageName = it.arguments?.getString("packageName")
            packageName?.let {
                AppInformationScreen(
                    packageName = packageName,
                    onBackClicked = {
                        navController.navigateUp()
                    }
                )
            }

        }

    }
}