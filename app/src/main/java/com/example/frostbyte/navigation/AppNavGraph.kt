package com.example.frostbyte.navigation
import com.example.frostbyte.ui.list.ListScreen
import com.example.frostbyte.ui.results.ResultsScreen
import com.example.frostbyte.ui.task.TaskScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frostbyte.data.AppContainer
import com.example.frostbyte.ui.home.HomeScreen
import com.example.frostbyte.viewmodel.HomeViewModel
import com.example.frostbyte.viewmodel.HomeViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController,
    appContainer: AppContainer
) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(
                    listsRepository = appContainer.listsRepository,
                    tasksRepository = appContainer.tasksRepository
                )
            )
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
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