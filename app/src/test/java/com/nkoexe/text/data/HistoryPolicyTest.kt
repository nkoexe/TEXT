package com.nkoexe.text.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HistoryPolicyTest {
    @Test
    fun recordsNewTextAtTheFront() {
        val current = listOf(entry("older", 1L), entry("oldest", 0L))

        val result = HistoryPolicy.record(current, "new", 2L)

        assertEquals(listOf("new", "older", "oldest"), result.texts())
        assertEquals(2L, result.first().lastShownAtEpochMillis)
        assertEquals(current, result.drop(1))
    }

    @Test
    fun exactDuplicateMovesToFrontAndRefreshesTimestamp() {
        val current = listOf(entry("older", 1L), entry("repeat", 2L), entry("newer", 3L))

        val result = HistoryPolicy.record(current, "repeat", 10L)

        assertEquals(listOf("repeat", "older", "newer"), result.texts())
        assertEquals(10L, result.first().lastShownAtEpochMillis)
        assertEquals(1, result.count { it.text == "repeat" })
        assertEquals(1L, result[1].lastShownAtEpochMillis)
        assertEquals(3L, result[2].lastShownAtEpochMillis)
    }

    @Test
    fun textMatchingIsExactCaseSensitiveAndDoesNotTrim() {
        val current = listOf(entry("hello", 1L), entry("Hello", 2L), entry(" hello ", 3L))

        val result = HistoryPolicy.record(current, "hello", 4L)

        assertEquals(listOf("hello", "Hello", " hello "), result.texts())
        assertEquals(4L, result[0].lastShownAtEpochMillis)
        assertEquals(2L, result[1].lastShownAtEpochMillis)
        assertEquals(3L, result[2].lastShownAtEpochMillis)
    }

    @Test
    fun preservesNewlinesAndWhitespaceExactly() {
        val content = "  first line\nsecond line\n  "
        val result = HistoryPolicy.record(emptyList(), content, 1L)

        assertEquals(content, result.single().text)
    }

    @Test
    fun blankTextIsNeverAdded() {
        val current = listOf(entry("kept", 1L))

        assertEquals(current, HistoryPolicy.record(current, " \n\t ", 2L))
        assertTrue(HistoryPolicy.record(emptyList(), "", 2L).isEmpty())
    }

    @Test
    fun newTextEvictsOldestEntryAtTheLimit() {
        val current = (1L..HistoryPolicy.MAX_ENTRIES).map { entry("text-$it", it) }

        val result = HistoryPolicy.record(current, "new", 99L)

        assertEquals(HistoryPolicy.MAX_ENTRIES, result.size)
        assertEquals("new", result.first().text)
        assertEquals("text-9", result.last().text)
        assertFalse(result.any { it.text == "text-10" })
    }

    @Test
    fun duplicatePromotionAtTheLimitDoesNotEvictAnotherEntry() {
        // The supplied list is already newest-first, so text-10 is the oldest entry.
        val current = (1L..HistoryPolicy.MAX_ENTRIES).map { entry("text-$it", it) }

        val result = HistoryPolicy.record(current, "text-10", 99L)

        assertEquals(HistoryPolicy.MAX_ENTRIES, result.size)
        assertEquals("text-10", result.first().text)
        assertEquals("text-1", result[1].text)
        assertEquals(1, result.count { it.text == "text-10" })
    }

    @Test
    fun blankInputStillCapsAndCleansLegacyHistory() {
        val current = listOf(
            entry("duplicate", 5L),
            entry("duplicate", 4L),
            entry(" ", 3L)
        ) + (1L..12L).map { entry("text-$it", it) }

        val result = HistoryPolicy.record(current, "\n", 99L)

        assertEquals(HistoryPolicy.MAX_ENTRIES, result.size)
        assertEquals(
            listOf("duplicate", "text-1", "text-2", "text-3", "text-4",
                "text-5", "text-6", "text-7", "text-8", "text-9"),
            result.texts()
        )
        assertTrue(result.none { it.text.isBlank() })
        assertEquals(5L, result.first().lastShownAtEpochMillis)
    }

    @Test
    fun repeatedNonTargetLegacyEntriesAreRemovedWithoutReorderingTheRest() {
        val current = listOf(
            entry("a", 3L), entry("b", 2L), entry("a", 1L), entry("c", 0L)
        )

        val result = HistoryPolicy.record(current, "new", 4L)

        assertEquals(listOf("new", "a", "b", "c"), result.texts())
        assertEquals(3L, result[1].lastShownAtEpochMillis)
    }

    private fun entry(text: String, timestamp: Long) = HistoryRecord(text, timestamp)
    private fun List<HistoryRecord>.texts() = map { it.text }
}
