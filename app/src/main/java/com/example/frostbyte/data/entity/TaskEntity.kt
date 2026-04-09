package com.example.frostbyte.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["listId"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("listId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0,
    val listId: Int,
    val title: String,
    val notes: String?,
    val dueDate: Long?,
    val importance: Int,
    val urgency: Int,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)