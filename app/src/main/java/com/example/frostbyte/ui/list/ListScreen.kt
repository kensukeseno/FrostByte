package com.example.frostbyte.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header

@Composable
fun ListScreen(
    navController: NavController,
    listId: Int
) {
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

        // Fake task list (placeholder only)
        val tasks = listOf(1, 2, 3)

        tasks.forEach { task ->

            Row {
                Text(text = "Task " + task)

                Button(
                    onClick = {
                        navController.navigate("task/$listId")
                    }
                ) {
                    Text("edit icon")
                }
            }
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
