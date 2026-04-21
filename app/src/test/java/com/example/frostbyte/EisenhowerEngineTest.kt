package com.example.frostbyte

import com.example.frostbyte.domain.EisenhowerEngine
import org.junit.Assert.*
import org.junit.Test

//Unit Tests for EisenhowerEngine

class EisenhowerEngineTest {

    // ── 1. High importance + high urgency -> "Do" ─────────────────────────────
    @Test
    fun categorizeTask_highImportanceHighUrgency_returnsDo() {
        val result = EisenhowerEngine.categorizeTask(importance = 5, urgency = 5)
        assertEquals("Do", result)
    }

    // ── 2. Importance exactly 4 + urgency exactly 4 -> "Do" (boundary) ────────
    @Test
    fun categorizeTask_boundaryFourFour_returnsDo() {
        val result = EisenhowerEngine.categorizeTask(importance = 4, urgency = 4)
        assertEquals("Do", result)
    }

    // ── 3. High importance + low urgency -> "Schedule" ────────────────────────
    @Test
    fun categorizeTask_highImportanceLowUrgency_returnsSchedule() {
        val result = EisenhowerEngine.categorizeTask(importance = 5, urgency = 2)
        assertEquals("Schedule", result)
    }

    // ── 4. Importance exactly 4 + urgency exactly 3 -> "Schedule" (boundary) ──
    @Test
    fun categorizeTask_boundaryFourThree_returnsSchedule() {
        val result = EisenhowerEngine.categorizeTask(importance = 4, urgency = 3)
        assertEquals("Schedule", result)
    }

    // ── 5. Low importance + high urgency -> "Delegate" ────────────────────────
    @Test
    fun categorizeTask_lowImportanceHighUrgency_returnsDelegate() {
        val result = EisenhowerEngine.categorizeTask(importance = 2, urgency = 5)
        assertEquals("Delegate", result)
    }

    // ── 6. Importance exactly 3 + urgency exactly 4 -> "Delegate" (boundary) ──
    @Test
    fun categorizeTask_boundaryThreeFour_returnsDelegate() {
        val result = EisenhowerEngine.categorizeTask(importance = 3, urgency = 4)
        assertEquals("Delegate", result)
    }

    // ── 7. Low importance + low urgency -> "Delete" ───────────────────────────
    @Test
    fun categorizeTask_lowImportanceLowUrgency_returnsDelete() {
        val result = EisenhowerEngine.categorizeTask(importance = 1, urgency = 1)
        assertEquals("Delete", result)
    }

    // ── 8. Importance 3 + urgency 3 -> "Delete" (both below threshold) ────────
    @Test
    fun categorizeTask_threeThree_returnsDelete() {
        val result = EisenhowerEngine.categorizeTask(importance = 3, urgency = 3)
        assertEquals("Delete", result)
    }

    // ── 9. Due within 24 hours boosts urgency -> low urgency becomes "Do" ─────
    @Test
    fun categorizeTask_nearDeadline_boostsUrgencyToDo() {
        // Task is important but urgency is low — normally "Schedule"
        // But due date is 1 hour from now, so urgency should auto-boost to 5
        val oneHourFromNow = System.currentTimeMillis() + (60 * 60 * 1000L)
        val result = EisenhowerEngine.categorizeTask(
            importance = 5,
            urgency = 1,
            dueDate = oneHourFromNow
        )
        assertEquals("Do", result)
    }

    // ── 10. Due date far in future does NOT boost urgency ────────────────────
    @Test
    fun categorizeTask_farDeadline_doesNotBoostUrgency() {
        // Task is important but urgency is low and due date is 7 days away
        // Should stay as "Schedule", not get boosted to "Do"
        val sevenDaysFromNow = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L)
        val result = EisenhowerEngine.categorizeTask(
            importance = 5,
            urgency = 1,
            dueDate = sevenDaysFromNow
        )
        assertEquals("Schedule", result)
    }

    // ── 11. Near deadline + low importance -> "Delegate" (not "Do") ───────────
    @Test
    fun categorizeTask_nearDeadlineLowImportance_returnsDelegate() {
        // Urgency boosted to 5 by near deadline, but importance is still low
        val twoHoursFromNow = System.currentTimeMillis() + (2 * 60 * 60 * 1000L)
        val result = EisenhowerEngine.categorizeTask(
            importance = 2,
            urgency = 1,
            dueDate = twoHoursFromNow
        )
        assertEquals("Delegate", result)
    }

    // ── 12. Null dueDate uses raw urgency value ───────────────────────────────
    @Test
    fun categorizeTask_nullDueDate_usesRawUrgency() {
        // Without a due date, urgency = 2 should not be boosted
        val result = EisenhowerEngine.categorizeTask(
            importance = 5,
            urgency = 2,
            dueDate = null
        )
        assertEquals("Schedule", result)
    }
}
