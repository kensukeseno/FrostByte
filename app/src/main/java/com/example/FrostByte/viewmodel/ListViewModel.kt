package com.example.FrostByte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.FrostByte.data.dao.ListDao
import com.example.FrostByte.data.entity.ListEntity

class ListViewModel(
    private val listDao: ListDao
) : ViewModel() {

    // 🔹 All lists (reactive)
    val allLists: Flow<List<ListEntity>> = listDao.getAllLists()

    // 🔹 Add new list
    fun addList(name: String) {
        viewModelScope.launch {
            listDao.insertList(
                ListEntity(name = name)
            )
        }
    }

    // 🔹 Delete list
    // (Tasks will auto-delete because of CASCADE in TaskEntity)
    fun deleteList(list: ListEntity) {
        viewModelScope.launch {
            listDao.deleteList(list)
        }
    }

    // 🔹 Rename list
    fun renameList(list: ListEntity, newName: String) {
        viewModelScope.launch {
            listDao.updateList(
                list.copy(name = newName)
            )
        }
    }
}