package com.example.frostbyte.ui.results

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import com.example.frostbyte.ui.components.CategoryCard
import com.example.frostbyte.ui.theme.tertiaryLight

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
                putExtra(
                    CalendarContract.EXTRA_EVENT_END_TIME,
                    it + 3600000
                ) // Default 1 hour duration
            }
        }
        context.startActivity(intent)
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Header(navController = navController, title = "Result")

            // DO Section
            CategoryCard(
                title = "Do",
                headerColor = MaterialTheme.colorScheme.primary
            ) {
                tasksDo.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(task.title, style = MaterialTheme.typography.bodyLarge)
                        Row {
                            TextButton(onClick = { taskViewModel.deleteTask(task) }) { Text("delete") }
                            TextButton(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("edit") }
                            }
                    }
                }
            }

            // SCHEDULE Section
            CategoryCard(
                title = "Schedule",
                headerColor = MaterialTheme.colorScheme.secondary
            ) {
                tasksSchedule.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(task.title, style = MaterialTheme.typography.bodyLarge)
                        TextButton(onClick = { scheduleTaskInCalendar(task) }) { Text("+add to calendar") }
                    }
                }
            }

            // DELEGATE Section
            CategoryCard(
                title = "Delegate",
                headerColor = MaterialTheme.colorScheme.tertiary
            ) {
                tasksDelegate.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(task.title, style = MaterialTheme.typography.bodyLarge)
                        Row {
                            TextButton(onClick = { taskViewModel.deleteTask(task) }) { Text("delete") }
                            TextButton(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("edit") }
                        }
                    }
                }
            }

            // DELETE Section
            CategoryCard(
                title = "Delete",
                headerColor = MaterialTheme.colorScheme.error
            ) {
                tasksDelete.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(task.title, style = MaterialTheme.typography.bodyLarge)
                        Row {
                            TextButton(onClick = { taskViewModel.deleteTask(task) }) { Text("delete") }
                            TextButton(onClick = { navController.navigate("task/$listId/${task.taskId}") }) { Text("edit") }
                        }
                    }
                }
            }
        }
    }
}