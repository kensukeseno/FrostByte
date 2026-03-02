package com.example.FrostByte.domain

object EisenhowerEngine {

    fun categorizeTask(importance: Int, urgency: Int): String {

        return when {
            importance >= 4 && urgency >= 4 -> "Do"
            importance >= 4 && urgency < 4 -> "Schedule"
            importance < 4 && urgency >= 4 -> "Delegate"
            else -> "Delete"
        }
    }
}