package com.nkoexe.text.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class HistoryPolicyPropertyTest {
    @Test
    fun outputInvariantsHoldAcrossMessyLegacyInputs() {
        val random = Random(20261022)
        val possibleTexts = listOf(
            "", " ", "\n", "alpha", "beta", "gamma", "alpha", " beta ",
            "line one\nline two", "\t", "🙂"
        )

        repeat(500) {
            val current = List(random.nextInt(0, 30)) {
                HistoryRecord(
                    text = possibleTexts.random(random),
                    lastShownAtEpochMillis = random.nextLong(-100L, 10_000L)
                )
            }
            val incoming = possibleTexts.random(random)
            val timestamp = random.nextLong(-100L, 10_000L)

            val result = HistoryPolicy.record(current, incoming, timestamp)

            assertTrue("history exceeded limit", result.size <= HistoryPolicy.MAX_ENTRIES)
            assertTrue("blank entry retained", result.none { it.text.isBlank() })
            assertEquals("duplicate text retained", result.size, result.map { it.text }.distinct().size)

            if (incoming.isBlank()) {
                val expected = current.asSequence()
                    .filter { it.text.isNotBlank() }
                    .distinctBy { it.text }
                    .take(HistoryPolicy.MAX_ENTRIES)
                    .toList()
                assertEquals(expected, result)
            } else {
                assertEquals(incoming, result.first().text)
                assertEquals(timestamp, result.first().lastShownAtEpochMillis)
                val expectedRemainder = current.asSequence()
                    .filter { it.text.isNotBlank() && it.text != incoming }
                    .distinctBy { it.text }
                    .take(HistoryPolicy.MAX_ENTRIES - 1)
                    .toList()
                assertEquals(expectedRemainder, result.drop(1))
            }
        }
    }

    @Test
    fun repeatedDisplayOfTheSameTextNeverCreatesMoreThanOneEntry() {
        var history = emptyList<HistoryRecord>()

        repeat(100) { index ->
            history = HistoryPolicy.record(history, "same text", index.toLong())
        }

        assertEquals(1, history.size)
        assertEquals("same text", history.single().text)
        assertEquals(99L, history.single().lastShownAtEpochMillis)
    }
}
