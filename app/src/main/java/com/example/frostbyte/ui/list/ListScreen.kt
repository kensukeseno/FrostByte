package com.example.frostbyte.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header
import com.example.frostbyte.ui.components.TaskItem
import com.example.frostbyte.viewmodel.TaskViewModel

@Composable
fun ListScreen(
    navController: NavController,
    listId: Int,
    taskViewModel: TaskViewModel
) {
    LaunchedEffect(listId) {
        taskViewModel.loadTasks(listId)
    }

    val tasks by taskViewModel.tasks.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(navController = navController, title = "List ID: $listId")

        // Add Task Button
        Button(
            onClick = {
                navController.navigate("task/$listId")
            }
        ) {
            Text("Add Task")
        }

        tasks.forEach { task ->
            TaskItem(
                task = task,
                onCheckChanged = {
                    taskViewModel.toggleTaskDone(task)
                },
                onClick = {
                    navController.navigate("task/$listId")
                },
                onDeleteClick = {
                    taskViewModel.deleteTask(task)
                }
            )
        }

        // Button to navigate to ResultsScreen
        Button(
            onClick = {
                navController.navigate("results/$listId")
            }
        ) {
            Text("Prioritize!")
        }
    }
}

