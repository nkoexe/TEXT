package com.nkoexe.text.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HistoryPolicyTest {
    @Test
    fun `blank text is not added`() {
        val history = listOf(HistoryRecord("hello", 1L))
        assertEquals(history, HistoryPolicy.record(history, " \n ", 2L))
    }

    @Test
    fun `new entry appears first and history is capped at ten`() {
        val history = (1L..10L).map { HistoryRecord("text-$it", it) }
        val result = HistoryPolicy.record(history, "new", 20L)

        assertEquals(10, result.size)
        assertEquals("new", result.first().text)
        assertEquals("text-9", result.last().text)
    }

    @Test
    fun `exact duplicate is moved to front without duplication`() {
        val history = listOf(
            HistoryRecord("older", 1L),
            HistoryRecord("message\nsecond line", 2L),
            HistoryRecord("newer", 3L)
        )
        val result = HistoryPolicy.record(history, "message\nsecond line", 10L)

        assertEquals(3, result.size)
        assertEquals("message\nsecond line", result[0].text)
        assertEquals(10L, result[0].lastShownAtEpochMillis)
        assertEquals(listOf("older", "newer"), result.drop(1).map { it.text })
    }

    @Test
    fun `text content is not trimmed`() {
        val result = HistoryPolicy.record(emptyList(), " hello ", 1L)
        assertEquals(" hello ", result.single().text)
    }

    @Test
    fun `over-limit input is reduced to ten even for blank text`() {
        val history = (1L..12L).map { HistoryRecord("text-$it", it) }
        val result = HistoryPolicy.record(history, "", 13L)
        assertEquals(10, result.size)
        assertTrue(result.none { it.text.isBlank() })
    }
}
