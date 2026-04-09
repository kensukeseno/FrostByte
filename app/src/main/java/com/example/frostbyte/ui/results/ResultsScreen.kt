package com.example.frostbyte.ui.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.frostbyte.domain.EisenhowerEngine
import com.example.frostbyte.viewmodel.TaskViewModel

@Composable
fun ResultsScreen(
    navController: NavController,
    listId: Int,
    taskViewModel: TaskViewModel
) {
    LaunchedEffect(listId) {
        taskViewModel.loadTasks(listId)
    }

    val tasks by taskViewModel.tasks.collectAsState()

    val tasksDo = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency) == "Do"
    }

    val tasksSchedule = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency) == "Schedule"
    }

    val tasksDelegate = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency) == "Delegate"
    }

    val tasksDelete = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency) == "Delete"
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Header(navController = navController, title = "Result")

        // DO Section
        Text(text = "DO (${tasksDo.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) { // each section scrollable
            items(tasksDo) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { /* Edit action */ }) { Text("Edit") }
                    Button(onClick = { /* Delete action */ }) { Text("Delete") }
                }
            }
        }

        // SCHEDULE Section
        Text(text = "SCHEDULE (${tasksSchedule.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksSchedule) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { /* Edit action */ }) { Text("Edit") }
                    Button(onClick = { /* Delete action */ }) { Text("Delete") }
                }
            }
        }

        // DELEGATE Section
        Text(text = "DELEGATE (${tasksDelegate.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksDelegate) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { /* Edit action */ }) { Text("Edit") }
                    Button(onClick = { /* Delete action */ }) { Text("Delete") }
                }
            }
        }

        // DELETE Section
        Text(text = "DELETE (${tasksDelete.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksDelete) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { /* Edit action */ }) { Text("Edit") }
                    Button(onClick = { /* Delete action */ }) { Text("Delete") }
                }
            }
        }
    }
}
