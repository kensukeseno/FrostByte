package com.example.frostbyte.data.repository

import com.example.frostbyte.data.entity.ListEntity
import kotlinx.coroutines.flow.Flow

interface ListsRepository {
    fun getAllLists(): Flow<List<ListEntity>>
    suspend fun getList(id: Int): ListEntity?
    suspend fun insertList(list: ListEntity)
    suspend fun updateList(list: ListEntity)
    suspend fun deleteList(list: ListEntity)
}