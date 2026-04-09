package com.example.FrostByte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.FrostByte.data.repository.ListsRepository

class ListViewModelFactory(
    private val listsRepository: ListsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(
                listsRepository = listsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}