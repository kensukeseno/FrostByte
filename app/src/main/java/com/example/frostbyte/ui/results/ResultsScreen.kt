package com.example.frostbyte.ui.results

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.frostbyte.data.entity.TaskEntity
import com.example.frostbyte.domain.EisenhowerEngine
import com.example.frostbyte.viewmodel.TaskViewModel

@Composable
fun ResultsScreen(
    navController: NavController,
    listId: Int,
    taskViewModel: TaskViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(listId) {
        taskViewModel.loadTasks(listId)
    }

    val tasks by taskViewModel.tasks.collectAsState()

    val tasksDo = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency, it.dueDate) == "Do"
    }

    val tasksSchedule = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency, it.dueDate) == "Schedule"
    }

    val tasksDelegate = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency, it.dueDate) == "Delegate"
    }

    val tasksDelete = tasks.filter {
        EisenhowerEngine.categorizeTask(it.importance, it.urgency, it.dueDate) == "Delete"
    }

    fun scheduleTaskInCalendar(task: TaskEntity) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, task.title)
            putExtra(CalendarContract.Events.DESCRIPTION, task.notes)
            task.dueDate?.let {
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, it)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, it + 3600000) // Default 1 hour duration
            }
        }
        context.startActivity(intent)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Header(navController = navController, title = "Result")

        // DO Section
        Text(text = "DO (${tasksDo.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) { // each section scrollable
            items(tasksDo) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("Edit") }
                    Button(onClick = { taskViewModel.deleteTask(task) }) { Text("Delete") }
                }
            }
        }

        // SCHEDULE Section
        Text(text = "SCHEDULE (${tasksSchedule.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksSchedule) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { scheduleTaskInCalendar(task) }) { Text("Schedule") }
                    Button(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("Edit") }
                    Button(onClick = { taskViewModel.deleteTask(task) }) { Text("Delete") }
                }
            }
        }

        // DELEGATE Section
        Text(text = "DELEGATE (${tasksDelegate.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksDelegate) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("Edit") }
                    Button(onClick = { taskViewModel.deleteTask(task) }) { Text("Delete") }
                }
            }
        }

        // DELETE Section
        Text(text = "DELETE (${tasksDelete.size})")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksDelete) { task ->
                Row {
                    Text(text = task.title)
                    Button(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("Edit") }
                    Button(onClick = { taskViewModel.deleteTask(task) }) { Text("Delete") }
                }
            }
        }
    }
}
