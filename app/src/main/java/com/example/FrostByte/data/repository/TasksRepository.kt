package com.example.FrostByte.data.repository

import com.example.FrostByte.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasksByListId(listId: Int): Flow<List<TaskEntity>>
    suspend fun getTask(id: Int): TaskEntity?
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
}