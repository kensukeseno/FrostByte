import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun ResultsScreen(navController: NavController, listId: Int) {
    // Fake tasks for demonstration
    val tasksDo = listOf("Task 1", "Task 2", "Task 3")
    val tasksSchedule = listOf("Task 4", "Task 5")
    val tasksDelegate = listOf("Task 6")
    val tasksDelete = listOf("Task 7", "Task 8")

    Column(modifier = Modifier.fillMaxSize()) {

        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text("Logo")
        }

        Text(text = "Result")

        // DO Section
        Text(text = "DO")
        LazyColumn(modifier = Modifier.height(100.dp)) { // each section scrollable
            items(tasksDo) { task ->
                Text(text = task)
            }
        }

        // SCHEDULE Section
        Text(text = "SCHEDULE")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksSchedule) { task ->
                Text(text = task)
            }
        }

        // DELEGATE Section
        Text(text = "DELEGATE")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksDelegate) { task ->
                Text(text = task)
            }
        }

        // DELETE Section
        Text(text = "DELETE")
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(tasksDelete) { task ->
                Text(text = task)
            }
        }
    }
}