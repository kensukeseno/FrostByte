package com.example.frostbyte

import com.example.frostbyte.data.entity.ListEntity
import com.example.frostbyte.data.entity.TaskEntity
import org.junit.Assert.*
import org.junit.Test

//Unit Tests

class TaskEntityTest {

    // ─── 1. TaskEntity default values ───────────────────────────────────────
    @Test
    fun taskEntity_defaultValues_areCorrect() {
        val task = TaskEntity(
            listId = 1,
            title = "Buy groceries",
            notes = null,
            dueDate = null,
            importance = 3,
            urgency = 2
        )
        assertEquals(0, task.taskId)           // autoGenerate default
        assertFalse(task.isDone)               // not done by default
        assertNull(task.notes)
        assertNull(task.dueDate)
    }

    // ─── 2. TaskEntity copy — toggle isDone ──────────────────────────────────
    @Test
    fun taskEntity_copy_togglesDone() {
        val task = TaskEntity(
            taskId = 5,
            listId = 1,
            title = "Write report",
            notes = null,
            dueDate = null,
            importance = 4,
            urgency = 4
        )
        val toggled = task.copy(isDone = !task.isDone)
        assertTrue(toggled.isDone)
        assertEquals(task.taskId, toggled.taskId) // same id
    }

    // ─── 3. TaskEntity copy — update fields ──────────────────────────────────
    @Test
    fun taskEntity_copy_updatesFields() {
        val task = TaskEntity(
            taskId = 3,
            listId = 1,
            title = "Old title",
            notes = "Old note",
            dueDate = null,
            importance = 1,
            urgency = 1
        )
        val updated = task.copy(title = "New title", importance = 5, urgency = 5)
        assertEquals("New title", updated.title)
        assertEquals(5, updated.importance)
        assertEquals(5, updated.urgency)
    }

    // ─── 4. ListEntity default values ────────────────────────────────────────
    @Test
    fun listEntity_defaultValues_areCorrect() {
        val list = ListEntity(name = "Work")
        assertEquals(0, list.listId)           // autoGenerate default
        assertEquals("Work", list.name)
        assertTrue(list.createdAt > 0)
    }

    // ─── 5. ListEntity copy — rename ─────────────────────────────────────────
    @Test
    fun listEntity_copy_renamesCorrectly() {
        val list = ListEntity(listId = 2, name = "Personal")
        val renamed = list.copy(name = "Personal Tasks")
        assertEquals("Personal Tasks", renamed.name)
        assertEquals(2, renamed.listId)        // id preserved
    }

    // ─── 6. TaskEntity importance boundary — min ─────────────────────────────
    @Test
    fun taskEntity_importanceMinValue_storesCorrectly() {
        val task = TaskEntity(
            listId = 1, title = "Low prio",
            notes = null, dueDate = null,
            importance = 1, urgency = 1
        )
        assertEquals(1, task.importance)
        assertEquals(1, task.urgency)
    }

    // ─── 7. TaskEntity importance boundary — max ─────────────────────────────
    @Test
    fun taskEntity_importanceMaxValue_storesCorrectly() {
        val task = TaskEntity(
            listId = 1, title = "Critical",
            notes = null, dueDate = null,
            importance = 5, urgency = 5
        )
        assertEquals(5, task.importance)
        assertEquals(5, task.urgency)
    }

    // ─── 8. TaskEntity with dueDate ──────────────────────────────────────────
    @Test
    fun taskEntity_dueDate_storesCorrectly() {
        val due = System.currentTimeMillis() + 86_400_000L // tomorrow
        val task = TaskEntity(
            listId = 1, title = "Submit assignment",
            notes = "Don't forget!", dueDate = due,
            importance = 4, urgency = 5
        )
        assertEquals(due, task.dueDate)
        assertEquals("Don't forget!", task.notes)
    }

    // ─── 9. Two tasks with same title are NOT equal when taskId differs ───────
    @Test
    fun taskEntity_differentIds_areNotEqual() {
        val task1 = TaskEntity(taskId = 1, listId = 1, title = "Task", notes = null, dueDate = null, importance = 2, urgency = 2)
        val task2 = TaskEntity(taskId = 2, listId = 1, title = "Task", notes = null, dueDate = null, importance = 2, urgency = 2)
        assertNotEquals(task1, task2)
    }

    // ─── 10. Priority score concept: importance * urgency ────────────────────
    @Test
    fun taskEntity_priorityScore_calculatesCorrectly() {
        val highPriority = TaskEntity(
            listId = 1, title = "Urgent+Important",
            notes = null, dueDate = null,
            importance = 5, urgency = 5
        )
        val lowPriority = TaskEntity(
            listId = 1, title = "Not urgent",
            notes = null, dueDate = null,
            importance = 1, urgency = 1
        )
        val highScore = highPriority.importance * highPriority.urgency
        val lowScore  = lowPriority.importance  * lowPriority.urgency
        assertTrue(highScore > lowScore)
        assertEquals(25, highScore)
        assertEquals(1,  lowScore)
    }
}
