package com.example.FrostByte.data.repository

import com.example.FrostByte.data.dao.TaskDao
import com.example.FrostByte.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(
    private val taskDao: TaskDao
) : TasksRepository {

    override fun getTasksByListId(listId: Int): Flow<List<TaskEntity>> =
        taskDao.getTasksByListId(listId)

    override suspend fun getTask(id: Int): TaskEntity? =
        taskDao.getTaskById(id)

    override suspend fun insertTask(task: TaskEntity) =
        taskDao.insertTask(task)

    override suspend fun updateTask(task: TaskEntity) =
        taskDao.updateTask(task)

    override suspend fun deleteTask(task: TaskEntity) =
        taskDao.deleteTask(task)
}