package com.nkoexe.text.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryRepositoryTest {
    private lateinit var context: Context
    private lateinit var repository: HistoryRepository

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase(DATABASE_NAME)
        repository = HistoryRepository(context)
    }

    @After
    fun tearDown() {
        repository.close()
        context.deleteDatabase(DATABASE_NAME)
    }

    @Test
    fun duplicatePromotionIsExactAndSurvivesReopeningDatabase() {
        repository.record("hello", 100L)
        repository.record(" hello\nworld ", 200L)
        repository.record("hello", 50L) // Reordering must not depend on wall-clock timestamps.

        assertEquals(
            listOf("hello", " hello\nworld "),
            repository.all().map { it.text }
        )
        assertEquals(50L, repository.all().first().lastShownAtEpochMillis)

        repository.close()
        repository = HistoryRepository(context)

        assertEquals(listOf("hello", " hello\nworld "), repository.all().map { it.text })
    }

    @Test
    fun blankTextIsIgnoredAndHistoryIsCappedAtTen() {
        repository.record("  \n ", 999L)
        (1..11).forEach { repository.record("message-$it", 1L) }

        val result = repository.all()

        assertEquals(10, result.size)
        assertEquals("message-11", result.first().text)
        assertEquals("message-2", result.last().text)
    }

    @Test
    fun deleteRemovesOnlyAnExactMatch() {
        repository.record("hello", 1L)
        repository.record(" Hello ", 2L)

        repository.delete("hello")

        assertEquals(listOf(" Hello "), repository.all().map { it.text })
    }

    private companion object {
        const val DATABASE_NAME = "text-history.db"
    }
}
