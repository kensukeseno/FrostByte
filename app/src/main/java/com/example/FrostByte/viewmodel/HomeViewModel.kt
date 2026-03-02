package com.example.FrostByte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.FrostByte.data.dao.ListDao
import com.example.FrostByte.data.dao.TaskDao
import com.example.FrostByte.data.entity.ListEntity
import com.example.FrostByte.data.entity.TaskEntity

class HomeViewModel(
    private val listDao: ListDao,
    private val taskDao: TaskDao
) : ViewModel() {

    // 🔹 Get all lists
    val allLists: Flow<List<ListEntity>> = listDao.getAllLists()

    // 🔹 Get tasks for a specific list
    fun getTasksForList(listId: Int): Flow<List<TaskEntity>> {
        return taskDao.getTasksByListId(listId)
    }

    // 🔹 Insert new list
    fun addList(name: String) {
        viewModelScope.launch {
            listDao.insertList(
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
        category: String = "General"
    ) {
        viewModelScope.launch {
            taskDao.insertTask(
                TaskEntity(
                    listId = listId,
                    title = title,
                    notes = notes,
                    dueDate = null,
                    importance = importance,
                    urgency = urgency,
                    category = category
                )
            )
        }
    }

    // 🔹 Mark task done
    fun toggleTaskDone(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.updateTask(
                task.copy(isDone = !task.isDone)
            )
        }
    }

    // 🔹 Delete task
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}