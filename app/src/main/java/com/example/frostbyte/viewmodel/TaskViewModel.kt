package com.example.frostbyte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frostbyte.data.repository.TasksRepository
import com.example.frostbyte.data.entity.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    // State of tasks for a list
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    // Add a new task
    fun addTask(listId: Int, title: String, importance: Int, urgency: Int) {
        val newTask = TaskEntity(
            listId = listId,
            title = title,
            notes = null,
            dueDate = null,
            importance = importance,
            urgency = urgency,
        )

        viewModelScope.launch {
            tasksRepository.insertTask(newTask)
            loadTasks(listId)
        }
    }

    // Load tasks from repository
    fun loadTasks(listId: Int) {
        viewModelScope.launch {
            tasksRepository.getTasksByListId(listId)
                .collect { taskList ->
                    _tasks.value = taskList
                }
        }
    }

    // Delete a task
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.deleteTask(task)
        }
    }

    // Update task if Done
    fun toggleTaskDone(task: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.updateTask(
                task.copy(isDone = !task.isDone)
            )
        }
    }
}