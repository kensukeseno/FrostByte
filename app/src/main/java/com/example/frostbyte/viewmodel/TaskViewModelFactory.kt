package com.example.frostbyte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frostbyte.data.repository.ListsRepository
import com.example.frostbyte.data.repository.TasksRepository

class TaskViewModelFactory(
    private val tasksRepository: TasksRepository,
    private val listsRepository: ListsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(
                tasksRepository = tasksRepository,
                listsRepository = listsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
