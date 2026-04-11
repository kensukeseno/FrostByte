package com.example.frostbyte.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.frostbyte.data.entity.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row {
        Text(text = task.title)
        Button(
            onClick = { onEditClick() }
        ) {
            Text("Edit")
        }
        Button(
            onClick = { onDeleteClick() }
        ) {
            Text("Delete")
        }
    }
}