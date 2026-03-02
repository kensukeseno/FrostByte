package com.example.FrostByte.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.FrostByte.data.dao.ListDao
import com.example.FrostByte.data.dao.TaskDao
import com.example.FrostByte.data.entity.ListEntity
import com.example.FrostByte.data.entity.TaskEntity

@Database(
    entities = [ListEntity::class, TaskEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun listDao(): ListDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "eisenflow_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}