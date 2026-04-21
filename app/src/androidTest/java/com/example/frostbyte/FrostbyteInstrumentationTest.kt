package com.example.frostbyte

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//Instrumentation Tests

@RunWith(AndroidJUnit4::class)
class FrostbyteInstrumentationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    private fun AddListDialog(onAdd: (String) -> Unit, onDismiss: () -> Unit) {
        var text by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("New List") },
            text = {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.testTag("listNameField"),
                    placeholder = { Text("List name") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onAdd(text) },
                    modifier = Modifier.testTag("confirmButton")
                ) { Text("Add") }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("cancelButton")
                ) { Text("Cancel") }
            }
        )
    }

    @Composable
    private fun TaskItem(title: String, isDone: Boolean, onToggle: () -> Unit, onDelete: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("taskItem_$title")
        ) {
            Checkbox(
                checked = isDone,
                onCheckedChange = { onToggle() },
                modifier = Modifier.testTag("checkbox_$title")
            )
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .testTag("taskTitle_$title")
            )
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("deleteButton_$title")
            ) {
                Text("X")
            }
        }
    }

    @Composable
    private fun AddTaskForm(onAdd: (String, Int, Int) -> Unit) {
        var title      by remember { mutableStateOf("") }
        var importance by remember { mutableStateOf(1) }
        var urgency    by remember { mutableStateOf(1) }
        Column(Modifier.padding(16.dp)) {
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.testTag("taskTitleField"),
                placeholder = { Text("Task title") }
            )
            Slider(
                value = importance.toFloat(),
                onValueChange = { importance = it.toInt() },
                valueRange = 1f..5f,
                modifier = Modifier.testTag("importanceSlider")
            )
            Slider(
                value = urgency.toFloat(),
                onValueChange = { urgency = it.toInt() },
                valueRange = 1f..5f,
                modifier = Modifier.testTag("urgencySlider")
            )
            Button(
                onClick = { onAdd(title, importance, urgency) },
                modifier = Modifier.testTag("addTaskButton")
            ) { Text("Add Task") }
        }
    }

    // ── Tests ────────────────────────────────────────────────────────────────

    // ── 1. Add-list dialog renders its text field and buttons ────────────────
    @Test
    fun addListDialog_showsInputAndButtons() {
        composeTestRule.setContent {
            AddListDialog(onAdd = {}, onDismiss = {})
        }
        composeTestRule.onNodeWithTag("listNameField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("confirmButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("cancelButton").assertIsDisplayed()
    }

    // ── 2. Typing in the list-name field reflects the input ──────────────────
    @Test
    fun addListDialog_typingUpdatesField() {
        composeTestRule.setContent {
            AddListDialog(onAdd = {}, onDismiss = {})
        }
        composeTestRule.onNodeWithTag("listNameField").performTextInput("Work Tasks")
        composeTestRule.onNodeWithTag("listNameField").assertTextContains("Work Tasks")
    }

    // ── 3. Confirm button calls onAdd with the entered name ──────────────────
    @Test
    fun addListDialog_confirmButton_callsOnAddWithName() {
        var capturedName = ""
        composeTestRule.setContent {
            AddListDialog(onAdd = { capturedName = it }, onDismiss = {})
        }
        composeTestRule.onNodeWithTag("listNameField").performTextInput("Personal")
        composeTestRule.onNodeWithTag("confirmButton").performClick()
        assert(capturedName == "Personal")
    }

    // ── 4. Cancel button calls onDismiss ─────────────────────────────────────
    @Test
    fun addListDialog_cancelButton_callsOnDismiss() {
        var dismissed = false
        composeTestRule.setContent {
            AddListDialog(onAdd = {}, onDismiss = { dismissed = true })
        }
        composeTestRule.onNodeWithTag("cancelButton").performClick()
        assert(dismissed)
    }

    // ── 5. TaskItem renders title and checkbox ────────────────────────────────
    @Test
    fun taskItem_rendersCorrectly() {
        composeTestRule.setContent {
            TaskItem(title = "Finish report", isDone = false, onToggle = {}, onDelete = {})
        }
        composeTestRule.onNodeWithTag("taskTitle_Finish report").assertIsDisplayed()
        composeTestRule.onNodeWithTag("checkbox_Finish report").assertIsDisplayed()
    }

    // ── 6. TaskItem checkbox is unchecked when isDone = false ─────────────────
    @Test
    fun taskItem_checkbox_uncheckedWhenNotDone() {
        composeTestRule.setContent {
            TaskItem(title = "Buy milk", isDone = false, onToggle = {}, onDelete = {})
        }
        composeTestRule.onNodeWithTag("checkbox_Buy milk").assertIsOff()
    }

    // ── 7. TaskItem checkbox is checked when isDone = true ────────────────────
    @Test
    fun taskItem_checkbox_checkedWhenDone() {
        composeTestRule.setContent {
            TaskItem(title = "Submit form", isDone = true, onToggle = {}, onDelete = {})
        }
        composeTestRule.onNodeWithTag("checkbox_Submit form").assertIsOn()
    }

    // ── 8. Tapping checkbox calls onToggle ───────────────────────────────────
    @Test
    fun taskItem_checkboxTap_callsOnToggle() {
        var toggled = false
        composeTestRule.setContent {
            TaskItem(title = "Walk dog", isDone = false, onToggle = { toggled = true }, onDelete = {})
        }
        composeTestRule.onNodeWithTag("checkbox_Walk dog").performClick()
        assert(toggled)
    }

    // ── 9. Tapping delete button calls onDelete ───────────────────────────────
    @Test
    fun taskItem_deleteButton_callsOnDelete() {
        var deleted = false
        composeTestRule.setContent {
            TaskItem(title = "Old task", isDone = false, onToggle = {}, onDelete = { deleted = true })
        }
        composeTestRule.onNodeWithTag("deleteButton_Old task").performClick()
        assert(deleted)
    }

    // ── 10. AddTaskForm — Add button triggers callback with title ─────────────
    @Test
    fun addTaskForm_addButton_triggersCallbackWithTitle() {
        var capturedTitle = ""
        composeTestRule.setContent {
            AddTaskForm(onAdd = { title, _, _ -> capturedTitle = title })
        }
        composeTestRule.onNodeWithTag("taskTitleField").performTextInput("Team meeting")
        composeTestRule.onNodeWithTag("addTaskButton").performClick()
        assert(capturedTitle == "Team meeting")
    }
}
