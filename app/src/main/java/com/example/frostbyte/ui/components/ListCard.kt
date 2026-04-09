package com.example.frostbyte.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListCard(
    listTitle: String,
    onClick: () -> Unit
) {
    Text(
        text = listTitle,
        modifier = Modifier.clickable { onClick() }
    )
}