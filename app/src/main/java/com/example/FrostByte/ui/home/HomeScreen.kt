package com.example.FrostByte.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    // Full screen container
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Center the content
    ) {
        Text(
            text = "Welcome to FrostByte!",
        )
    }
}