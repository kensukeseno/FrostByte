package com.example.frostbyte.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            Spacer(modifier = Modifier.height(16.dp))

            // Add Task Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { navController.navigate("task/$listId") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("+ Add Task")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onEditClick = { navController.navigate("task/$listId/${task.taskId}") },
                        onDeleteClick = { taskViewModel.deleteTask(task) }
                    )
                }
            }
            // Button to navigate to ResultsScreen
            Button(
                onClick = {
                    navController.navigate("results/$listId")
                },
                        modifier = Modifier
                        .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text("Prioritize!")
            }
        }
    }
}
