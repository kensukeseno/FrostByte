package com.example.frostbyte.data.dao

import androidx.room.*
import com.example.frostbyte.data.entity.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert
    suspend fun insertList(list: ListEntity)

    @Update
    suspend fun updateList(list: ListEntity)

    @Delete
    suspend fun deleteList(list: ListEntity)

    @Query("SELECT * FROM lists ORDER BY createdAt DESC")
    fun getAllLists(): Flow<List<ListEntity>>

    @Query("SELECT * FROM lists WHERE listId = :id")
    suspend fun getListById(id: Int): ListEntity?
}