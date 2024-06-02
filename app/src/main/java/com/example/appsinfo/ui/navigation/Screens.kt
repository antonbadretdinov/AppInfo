package com.example.appsinfo.ui.navigation

sealed class Screens(
    val route: String
) {
    data object AppsListScreen: Screens(
        route = "AppsList"
    )

    data object AppInformationScreen: Screens(
        route = "AppInformation/{packageName}"
    )
}