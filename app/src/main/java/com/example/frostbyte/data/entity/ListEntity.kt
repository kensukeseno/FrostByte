package com.example.frostbyte.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class ListEntity(
    @PrimaryKey(autoGenerate = true)
    val listId: Int = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)