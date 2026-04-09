package com.example.frostbyte.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.frostbyte.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE listId = :listId ORDER BY createdAt DESC")
    fun getTasksByListId(listId: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE taskId = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}