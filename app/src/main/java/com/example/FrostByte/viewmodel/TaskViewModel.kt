package com.example.FrostByte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.FrostByte.data.TaskRepository
import com.example.FrostByte.data.entity.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
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
            repository.insertTask(newTask)
            loadTasks(listId)
        }
    }

    // Load tasks from repository
    fun loadTasks(listId: Int) {
        viewModelScope.launch {
            repository.getTasksByList(listId)
                .collect { taskList ->
                    _tasks.value = taskList
                }
        }
    }
}