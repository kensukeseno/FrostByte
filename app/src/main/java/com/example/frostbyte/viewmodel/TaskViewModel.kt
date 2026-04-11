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
    private val _selectedTask = MutableStateFlow<TaskEntity?>(null)
    val selectedTask: StateFlow<TaskEntity?> = _selectedTask

    // Add a new task
    fun addTask(
        listId: Int,
        title: String,
        notes: String?,
        importance: Int,
        urgency: Int,
        dueDate: Long? = null
    ) {
        val newTask = TaskEntity(
            listId = listId,
            title = title,
            notes = notes,
            dueDate = dueDate,
            importance = importance,
            urgency = urgency,
        )

        viewModelScope.launch {
            tasksRepository.insertTask(newTask)
            loadTasks(listId)
        }
    }

    // Load specific existing task
    fun loadTask(taskId: Int) {
        viewModelScope.launch {
            _selectedTask.value = tasksRepository.getTask(taskId)
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

    // Update task fields
    fun updateTask(
        taskId: Int,
        listId: Int,
        title: String,
        notes: String?,
        importance: Int,
        urgency: Int,
        dueDate: Long? = null
    ) {
        viewModelScope.launch {
            val existingTask = tasksRepository.getTask(taskId)
            if (existingTask != null) {
                tasksRepository.updateTask(
                    existingTask.copy(
                        listId = listId,
                        title = title,
                        notes = notes,
                        importance = importance,
                        urgency = urgency,
                        dueDate = dueDate
                    )
                )
            }
        }
    }
}
