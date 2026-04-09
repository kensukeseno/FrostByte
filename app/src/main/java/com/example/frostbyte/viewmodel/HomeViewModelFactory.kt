package com.example.frostbyte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frostbyte.data.repository.ListsRepository
import com.example.frostbyte.data.repository.TasksRepository

class HomeViewModelFactory(
    private val listsRepository: ListsRepository,
    private val tasksRepository: TasksRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                listsRepository = listsRepository,
                tasksRepository = tasksRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}