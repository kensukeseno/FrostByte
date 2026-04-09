package com.example.frostbyte.data

import com.example.frostbyte.data.dao.ListDao
import com.example.frostbyte.data.dao.TaskDao
import com.example.frostbyte.data.entity.ListEntity
import com.example.frostbyte.data.entity.TaskEntity

class TaskRepository(
    private val taskDao: TaskDao,
    private val listDao: ListDao
) {

    fun getAllLists() = listDao.getAllLists()

    suspend fun getListById(id: Int): ListEntity? {
        return listDao.getListById(id)
    }

    fun getTasksByList(listId: Int) =
        taskDao.getTasksByListId(listId)

    suspend fun getTaskById(id: Int): TaskEntity? {
        return taskDao.getTaskById(id)
    }

    suspend fun insertList(name: String) {
        listDao.insertList(ListEntity(name = name))
    }

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    suspend fun updateList(list: ListEntity) {
        listDao.updateList(list)
    }

    suspend fun deleteList(list: ListEntity) {
        listDao.deleteList(list)
    }
}