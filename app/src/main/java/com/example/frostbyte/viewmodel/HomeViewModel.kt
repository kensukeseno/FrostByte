package com.example.frostbyte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frostbyte.data.entity.ListEntity
import com.example.frostbyte.data.entity.TaskEntity
import com.example.frostbyte.data.repository.ListsRepository
import com.example.frostbyte.data.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val listsRepository: ListsRepository,
    private val tasksRepository: TasksRepository
) : ViewModel() {

    // 🔹 Get all lists
    val allLists: Flow<List<ListEntity>> = listsRepository.getAllLists()

    // 🔹 Get tasks for a specific list
    fun getTasksForList(listId: Int): Flow<List<TaskEntity>> {
        return tasksRepository.getTasksByListId(listId)
    }

    // 🔹 Insert new list
    fun addList(name: String) {
        viewModelScope.launch {
            listsRepository.insertList(
                ListEntity(name = name)
            )
        }
    }

    // 🔹 Insert new task
    fun addTask(
        listId: Int,
        title: String,
        notes: String? = null,
        importance: Int = 0,
        urgency: Int = 0,
    ) {
        viewModelScope.launch {
            tasksRepository.insertTask(
                TaskEntity(
                    listId = listId,
                    title = title,
                    notes = notes,
                    dueDate = null,
                    importance = importance,
                    urgency = urgency,
                )
            )
        }
    }

    // 🔹 Mark task done
    fun toggleTaskDone(task: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.updateTask(
                task.copy(isDone = !task.isDone)
            )
        }
    }

    // 🔹 Delete task
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.deleteTask(task)
        }
    }
}