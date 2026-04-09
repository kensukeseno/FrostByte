package com.example.FrostByte.data

import android.content.Context
import com.example.FrostByte.data.repository.ListsRepository
import com.example.FrostByte.data.repository.OfflineListsRepository
import com.example.FrostByte.data.repository.OfflineTasksRepository
import com.example.FrostByte.data.repository.TasksRepository

class AppContainer(context: Context) {

    // One place to assemble the ...

    // ... DATABASE
    private val database = AppDatabase.getDatabase(context)

    // ... and REPOSITORIES
    val listsRepository: ListsRepository by lazy {
        OfflineListsRepository(database.listDao())
    }

    val tasksRepository: TasksRepository by lazy {
        OfflineTasksRepository(database.taskDao())
    }
}