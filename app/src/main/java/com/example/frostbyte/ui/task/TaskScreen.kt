package com.example.frostbyte.ui.task

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header
import com.example.frostbyte.viewmodel.TaskViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
@Composable
fun TaskScreen(
    navController: NavController,
    listId: Int,
    taskViewModel: TaskViewModel
) {
    var importance by remember { mutableStateOf(1f) }
    var urgency by remember { mutableStateOf(3f) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Within timeframe") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var selectedDeadlineIndex by remember { mutableStateOf<Int?>(null) }


    val options = listOf(
        "1 hour",
        "1 day",
        "3 days",
        "1 week"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Header(navController = navController, title = "Task $listId")

        Text(text = "Task Title")

        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Enter task title") }
        )

        // -----------------------
        // Deadline Section
        // -----------------------
        Text(text = "Deadline")

        Row {
            Checkbox(
                checked = selectedDeadlineIndex == 0,
                onCheckedChange = { checked ->
                    selectedDeadlineIndex = if (checked) 0 else null
                }
            )
            Button(
                onClick = { expanded = true },
                enabled = selectedDeadlineIndex == 0
            ) {
                Text(selectedOption)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }
        Row {
            Checkbox(
                checked = selectedDeadlineIndex == 1,
                onCheckedChange = { checked ->
                    selectedDeadlineIndex = if (checked) 1 else null
                }
            )
            Button(
                onClick = { showDatePicker = true },
                enabled = selectedDeadlineIndex == 1
            ) {
                Text("Select Date")
            }

            Button(
                onClick = { showTimePicker = true },
                enabled = selectedDeadlineIndex == 1
            ) {
                Text("Select Time")
            }

            if (selectedDate != null) {
                Text("Date selected: $selectedDate")
            }

            // ---- Date Picker Dialog ----
            if (showDatePicker) {

                val datePickerState = rememberDatePickerState()

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

            // ---- Time Picker Dialog ----
            if (showTimePicker) {

                val timePickerState = rememberTimePickerState()

                AlertDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                selectedHour = timePickerState.hour
                                selectedMinute = timePickerState.minute
                                showTimePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    text = {
                        TimePicker(state = timePickerState)
                    }
                )
            }
        }

        // -----------------------
        // Notes Section
        // -----------------------
        Text(text = "Notes")

        TextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Enter notes / consequence") }
        )

        // -----------------------
        // Impact Section
        // -----------------------
        Text(text = "Importance")

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
        Text(text = "Urgency")

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

        Button(
            onClick = {
                if (taskTitle.isNotBlank()) {
                    taskViewModel.addTask(
                        listId = listId,
                        title = taskTitle.trim(),
                        importance = importance.toInt(),
                        urgency = urgency.toInt()
                    )
                    navController.popBackStack()
                }
            }
        ) {
            Text("Save Task")
        }

    }
}
