package com.example.frostbyte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frostbyte.data.entity.ListEntity
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

    // 🔹 Insert new list
    fun addList(name: String) {
        viewModelScope.launch {
            listsRepository.insertList(
                ListEntity(name = name)
            )
        }
    }

    // Delete existing List
    fun deleteList(list: ListEntity) {
        viewModelScope.launch {
            listsRepository.deleteList(list)
        }
    }


}