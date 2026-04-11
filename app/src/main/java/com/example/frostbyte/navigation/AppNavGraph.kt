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
import com.example.frostbyte.viewmodel.TaskViewModel
import com.example.frostbyte.viewmodel.TaskViewModelFactory

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

            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(
                    tasksRepository = appContainer.tasksRepository,
                    listsRepository = appContainer.listsRepository
                )
            )

            ListScreen(
                navController = navController,
                listId = listId,
                taskViewModel = taskViewModel
            )
        }

        composable("task/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0

            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(
                    tasksRepository = appContainer.tasksRepository,
                    listsRepository = appContainer.listsRepository
                )
            )

            TaskScreen(
                navController = navController,
                listId = listId,
                taskViewModel = taskViewModel
            )
        }

        composable("task/{listId}/{taskId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0
            val taskId = backStackEntry.arguments?.getString("taskId")?.toInt() ?: 0

            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(
                    tasksRepository = appContainer.tasksRepository,
                    listsRepository = appContainer.listsRepository
                )
            )

            TaskScreen(
                navController = navController,
                listId = listId,
                taskViewModel = taskViewModel,
                taskId = taskId
            )
        }

        composable("results/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0

            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(
                    tasksRepository = appContainer.tasksRepository,
                    listsRepository = appContainer.listsRepository
                )
            )

            ResultsScreen(
                navController = navController,
                listId = listId,
                taskViewModel = taskViewModel
            )
        }
    }
}
