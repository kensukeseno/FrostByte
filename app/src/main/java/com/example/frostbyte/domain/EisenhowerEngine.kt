package com.example.frostbyte.domain

object EisenhowerEngine {

    /**
     * Categorizes a task into one of the four Eisenhower Matrix quadrants.
     * Incorporates auto-urgency if the due date is within the next 24 hours.
     * Threshold set to 3 to capture more tasks as important/urgent.
     */
    fun categorizeTask(importance: Int, urgency: Int, dueDate: Long? = null): String {
        val currentTime = System.currentTimeMillis()
        val isNearDeadline = dueDate != null && (dueDate - currentTime) < 86400000 // 24 hours

        // If it's due very soon, treat it as high urgency (5)
        val effectiveUrgency = if (isNearDeadline) 5 else urgency

        return when {
            importance >= 4 && effectiveUrgency >= 4 -> "Do"
            importance >= 4 && effectiveUrgency < 4 -> "Schedule"
            importance < 4 && effectiveUrgency >= 4 -> "Delegate"
            else -> "Delete"
        }
    }
}
