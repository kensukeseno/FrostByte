package com.example.frostbyte.ui.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header
import com.example.frostbyte.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navController: NavController,
    listId: Int,
    taskViewModel: TaskViewModel,
    taskId: Int? = null
) {
    var importance by remember { mutableStateOf(1f) }
    var urgency by remember { mutableStateOf(3f) }

    var showDatePicker by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableStateOf("23") }
    var selectedMinute by remember { mutableStateOf("59") }

    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val selectedTask by taskViewModel.selectedTask.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Load the task if taskId is present
    LaunchedEffect(taskId) {
        if (taskId != null) {
            taskViewModel.loadTask(taskId)
        }
    }

    // Prefill the fields when the task above loads
    LaunchedEffect(selectedTask) {
        selectedTask?.let { task ->
            taskTitle = task.title
            notes = task.notes ?: ""
            importance = task.importance.toFloat()
            urgency = task.urgency.toFloat()
            
            if (task.dueDate != null) {
                val cal = Calendar.getInstance().apply { timeInMillis = task.dueDate }
                
                // Convert back to UTC Midnight for the selectedDate state
                val utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    clear()
                    set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                }
                selectedDate = utcCal.timeInMillis
                
                selectedHour = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))
                selectedMinute = String.format("%02d", cal.get(Calendar.MINUTE))
            }
        }
    }

    fun calculateFinalDueDate(): Long? {
        return selectedDate?.let { date ->
            val hour = selectedHour.toIntOrNull()?.coerceIn(0, 23) ?: 23
            val minute = selectedMinute.toIntOrNull()?.coerceIn(0, 59) ?: 59
            
            // The date from DatePicker is UTC midnight. 
            // We extract components using UTC to avoid timezone shifts.
            val utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = date
            }
            
            Calendar.getInstance().apply {
                set(Calendar.YEAR, utcCal.get(Calendar.YEAR))
                set(Calendar.MONTH, utcCal.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, utcCal.get(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Header(navController = navController, title = "Task Details")

            Text(text = "Task Title", modifier = Modifier.padding(16.dp))

            TextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text("Enter task title") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                isError = taskTitle.isBlank()
            )

            // -----------------------
            // Deadline Section
            // -----------------------
            Text(text = "Deadline", modifier = Modifier.padding(top = 16.dp, start = 16.dp))

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showDatePicker = true },
                    colors = if (selectedDate == null) ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error) else ButtonDefaults.buttonColors()
                ) {
                    val displayText = if (selectedDate == null) {
                        "Select Date"
                    } else {
                        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        sdf.timeZone = TimeZone.getTimeZone("UTC") // Force display to UTC
                        sdf.format(selectedDate)
                    }
                    Text(displayText)
                }

                OutlinedTextField(
                    value = selectedHour,
                    onValueChange = { if (it.length <= 2) selectedHour = it.filter { char -> char.isDigit() } },
                    modifier = Modifier.width(65.dp),
                    label = { Text("HH") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Text(":")
                OutlinedTextField(
                    value = selectedMinute,
                    onValueChange = { if (it.length <= 2) selectedMinute = it.filter { char -> char.isDigit() } },
                    modifier = Modifier.width(65.dp),
                    label = { Text("MM") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                selectedDate = datePickerState.selectedDateMillis
                                showDatePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // -----------------------
            // Notes Section
            // -----------------------
            Text(text = "Notes", modifier = Modifier.padding(top = 16.dp, start = 16.dp))

            TextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Enter notes / consequence") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            // -----------------------
            // Impact Section
            // -----------------------
            Text(text = "Importance", modifier = Modifier.padding(top = 16.dp, start = 16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Slider(
                    value = importance,
                    onValueChange = { importance = it },
                    valueRange = 1f..5f,
                    steps = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("!")
                    Text("!!")
                    Text("!!!")
                    Text("!!!!")
                    Text("!!!!!")
                }
            }

            // -----------------------
            // Urgency Section
            // -----------------------
            Text(text = "Urgency", modifier = Modifier.padding(top = 16.dp, start = 16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Slider(
                    value = urgency,
                    onValueChange = { urgency = it },
                    valueRange = 1f..5f,
                    steps = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1")
                    Text("2")
                    Text("3")
                    Text("4")
                    Text("5")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (taskTitle.isNotBlank() && selectedDate != null) {
                        val finalDueDate = calculateFinalDueDate()
                        if (taskId == null) {
                            taskViewModel.addTask(
                                listId = listId,
                                title = taskTitle.trim(),
                                notes = notes.ifBlank { null },
                                importance = importance.toInt(),
                                urgency = urgency.toInt(),
                                dueDate = finalDueDate
                            )
                        } else {
                            taskViewModel.updateTask(
                                taskId = taskId,
                                listId = listId,
                                title = taskTitle.trim(),
                                notes = notes.ifBlank { null },
                                importance = importance.toInt(),
                                urgency = urgency.toInt(),
                                dueDate = finalDueDate
                            )
                        }
                        navController.popBackStack()
                    } else {
                        scope.launch {
                            val message = if (taskTitle.isBlank() && selectedDate == null) {
                                "Title and Date are required"
                            } else if (taskTitle.isBlank()) {
                                "Title is required"
                            } else {
                                "Date is required"
                            }
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Save Task")
            }
        }
    }
}
