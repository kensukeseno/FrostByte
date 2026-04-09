package com.example.FrostByte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.FrostByte.data.repository.ListsRepository
import com.example.FrostByte.data.entity.ListEntity

class ListViewModel(
    private val listsRepository: ListsRepository
) : ViewModel() {

    // 🔹 All lists (reactive)
    val allLists: Flow<List<ListEntity>> = listsRepository.getAllLists()

    // 🔹 Add new list
    fun addList(name: String) {
        viewModelScope.launch {
            listsRepository.insertList(
                ListEntity(name = name)
            )
        }
    }

    // 🔹 Delete list
    // (Tasks will auto-delete because of CASCADE in TaskEntity)
    fun deleteList(list: ListEntity) {
        viewModelScope.launch {
            listsRepository.deleteList(list)
        }
    }

    // 🔹 Rename list
    fun renameList(list: ListEntity, newName: String) {
        viewModelScope.launch {
            listsRepository.updateList(
                list.copy(name = newName)
            )
        }
    }
}