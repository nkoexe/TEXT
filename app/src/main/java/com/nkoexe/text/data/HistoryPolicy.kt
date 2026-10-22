package com.nkoexe.text.data

/** Minimal, deterministic rules for recording a displayed text in history. */
data class HistoryRecord(
    val text: String,
    val lastShownAtEpochMillis: Long
)

object HistoryPolicy {
    const val MAX_ENTRIES: Int = 10

    /**
     * Adds a displayed text to the front, moves exact duplicates to the front,
     * and drops entries beyond [MAX_ENTRIES]. Text content is never normalized.
     * Blank text is not recorded; existing history is still reduced to the configured cap.
     */
    fun record(
        current: List<HistoryRecord>,
        text: String,
        timestamp: Long
    ): List<HistoryRecord> {
        if (text.isBlank()) return current.take(MAX_ENTRIES)

        val next = HistoryRecord(text = text, lastShownAtEpochMillis = timestamp)
        return buildList {
            add(next)
            current.asSequence()
                .filterNot { it.text == text }
                .take(MAX_ENTRIES - 1)
                .forEach(::add)
        }
    }
}
