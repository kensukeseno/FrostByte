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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TaskScreen(navController: NavController, listId: Int) {
    var impact by remember { mutableStateOf(1f) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Within timeframe") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }


    val options = listOf(
        "1 hour",
        "1 day",
        "3 days",
        "1 week"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = { navController.navigate("home") }
        ) {
            Text("Logo")
        }

        Text(text = "Task $listId")

        // -----------------------
        // Deadline Section
        // -----------------------
        Text(text = "Deadline")

        Row {
            Checkbox(
                checked = false,
                onCheckedChange = { }
            )
            Button(
                onClick = { expanded = true }
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
                checked = false,
                onCheckedChange = { }
            )
            Button(onClick = { showDatePicker = true }) {
                Text("Select Date")
            }

            Button(onClick = { showTimePicker = true }) {
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
        // Consequence Section
        // -----------------------
        Text(text = "Consequence")

        TextField(
            value = "",
            onValueChange = { },
            label = { Text("Enter consequence description") }
        )

        // -----------------------
        // Impact Section
        // -----------------------
        Text(text = "Impact")

        Slider(
            value = impact,
            onValueChange = { impact = it },
            valueRange = 1f..5f,
            steps = 3
        )

        Text(
            text = when (impact.toInt()) {
                1 -> "!"
                2 -> "!!"
                3 -> "!!!"
                4 -> "!!!!"
                else -> "!!!!!"
            }
        )
    }
}