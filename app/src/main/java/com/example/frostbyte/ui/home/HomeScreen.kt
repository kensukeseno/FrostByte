package com.example.frostbyte.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header

@Composable
fun HomeScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var listName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(navController = navController, title = "Task Lists")

        // Add Category Button
        Button(
            onClick = { showDialog = true }
        ) {
            Text("Add List")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showDialog = false
                    listName = "" 
                },
                title = { Text("Add New List") },
                text = {
                    TextField(
                        value = listName,
                        onValueChange = { listName = it },
                        label = { Text("List Name") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // TODO: Save listName to database
                            showDialog = false
                            listName = ""
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { 
                        showDialog = false
                        listName = ""
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Fake category list (placeholder data)
        val categories = listOf("Home", "Work", "School")

        categories.forEach { category ->
            Row{
                Text(
                    text = category,
                )
                Button(
                    onClick = {
                        // Navigate to ListScreen with category id
                        navController.navigate("list/1") // The category ID is set to 1 temporarily before db integration
                    }
                ) {
                    Text("edit icon")
                }
            }
        }
    }
}
