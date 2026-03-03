package com.example.FrostByte.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text("Logo")
        }

        // Title
        Text(text = "Task Lists")

        // Add Category Button
        Button(
            onClick = { /* TODO: Add category */ }
        ) {
            Text("Add Tasks")
        }

        // Fake category list (placeholder data)
        val categories = listOf("Home", "Work", "School")

        categories.forEach { category ->
            Text(
                text = category,
                modifier = Modifier.clickable {
                    // Navigate to ListScreen with category id
                    navController.navigate("list/1") // The category ID is set to 1 temporarily before db integration
                }
            )
        }
    }
}