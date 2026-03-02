package com.example.FrostByte.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.FrostByte.data.entity.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    onCheckChanged: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { checked -> onCheckChanged(checked) }
        )
        Text(text = task.title)
    }
}