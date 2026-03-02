package com.example.FrostByte.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.FrostByte.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE listId = :listId")
    fun getTasksByListId(listId: Int): Flow<List<TaskEntity>>

    @Delete
    fun deleteTask(task: TaskEntity)
}