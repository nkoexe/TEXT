package com.nkoexe.text.data

/**
 * One history entry. Lists returned by [HistoryPolicy] and [HistoryRepository]
 * are ordered newest first.
 */
data class HistoryRecord(
    val text: String,
    val lastShownAtEpochMillis: Long
)

/**
 * Pure history rules, used by persistence code and tested without an Android runtime.
 * Text is compared exactly and never normalized.
 */
object HistoryPolicy {
    const val MAX_ENTRIES = 10

    /**
     * Records [text] at the front, moves an exact duplicate rather than copying it,
     * and retains no more than [MAX_ENTRIES] unique, non-blank entries.
     *
     * [current] is expected to be newest-first. Invalid legacy entries are filtered
     * defensively, and the relative order/timestamps of retained entries are preserved.
     */
    fun record(
        current: List<HistoryRecord>,
        text: String,
        timestamp: Long
    ): List<HistoryRecord> {
        val existing = current.asSequence()
            .filter { it.text.isNotBlank() && it.text != text }
            .distinctBy { it.text }
            .take(MAX_ENTRIES - if (text.isBlank()) 0 else 1)

        if (text.isBlank()) return existing.toList()

        return buildList {
            add(HistoryRecord(text = text, lastShownAtEpochMillis = timestamp))
            existing.forEach(::add)
        }
    }
}
