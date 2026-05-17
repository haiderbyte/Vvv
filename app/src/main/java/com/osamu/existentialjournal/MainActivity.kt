package com.osamu.existentialjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osamu.existentialjournal.ui.theme.ExistentialTheme
import com.osamu.existentialjournal.ui.screens.HomeScreen
import com.osamu.existentialjournal.ui.screens.AddEntryScreen
import com.osamu.existentialjournal.ui.screens.SettingsScreen
import com.osamu.existentialjournal.ui.screens.EditEntryScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        
        setContent {
            ExistentialTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                onAddClick = { navController.navigate("add") },
                                onSettingsClick = { navController.navigate("settings") },
                                onEntryClick = { id -> navController.navigate("edit/$id") }
                            )
                        }
                        composable("add") {
                            AddEntryScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            "edit/{entryId}",
                            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val entryId = backStackEntry.arguments?.getLong("entryId") ?: 0L
                            EditEntryScreen(
                                entryId = entryId,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
