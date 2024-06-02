package com.example.appsinfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.appsinfo.ui.navigation.NavGraph
import com.example.appsinfo.ui.theme.AppsInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppsInfoTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}