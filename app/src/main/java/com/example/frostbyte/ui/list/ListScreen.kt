package com.example.frostbyte.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
    val listName by taskViewModel.listName.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Header(navController = navController, title = listName)

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
                    onEditClick = {
                        navController.navigate("task/$listId/${task.taskId}")
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
}
