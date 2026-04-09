package com.example.frostbyte.navigation
import com.example.frostbyte.ui.list.ListScreen
import com.example.frostbyte.ui.results.ResultsScreen
import com.example.frostbyte.ui.task.TaskScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frostbyte.ui.home.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("list/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0
            ListScreen(navController, listId)
        }

        composable("task/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0
            TaskScreen(navController, listId)
        }

        composable("results/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0
            ResultsScreen(navController, listId)
        }
    }
}