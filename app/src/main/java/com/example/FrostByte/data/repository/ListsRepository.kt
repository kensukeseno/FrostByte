package com.example.FrostByte.data.repository

import com.example.FrostByte.data.entity.ListEntity
import kotlinx.coroutines.flow.Flow

interface ListsRepository {
    fun getAllLists(): Flow<List<ListEntity>>
    suspend fun getList(id: Int): ListEntity?
    suspend fun insertList(list: ListEntity)
    suspend fun updateList(list: ListEntity)
    suspend fun deleteList(list: ListEntity)
}