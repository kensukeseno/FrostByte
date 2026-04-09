package com.example.FrostByte.data.repository

import com.example.FrostByte.data.dao.ListDao
import com.example.FrostByte.data.entity.ListEntity
import kotlinx.coroutines.flow.Flow

class OfflineListsRepository(
    private val listDao: ListDao
) : ListsRepository {

    override fun getAllLists(): Flow<List<ListEntity>> =
        listDao.getAllLists()

    override suspend fun getList(id: Int): ListEntity? =
        listDao.getListById(id)

    override suspend fun insertList(list: ListEntity) =
        listDao.insertList(list)

    override suspend fun updateList(list: ListEntity) =
        listDao.updateList(list)

    override suspend fun deleteList(list: ListEntity) =
        listDao.deleteList(list)
}