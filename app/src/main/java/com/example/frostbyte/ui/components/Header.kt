package com.example.frostbyte.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Header(navController: NavController, title: String) {
    Row {
        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text("Logo")
        }

        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text("Back")
        }

        Text(text = title)
    }
}
