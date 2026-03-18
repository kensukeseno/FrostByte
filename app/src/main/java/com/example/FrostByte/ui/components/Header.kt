package com.example.FrostByte.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Header(navController: NavController, title: String) {
    Button(
        onClick = { navController.navigate("home") }
    ) {
        Text("Logo")
    }

    Text(text = title)
}
