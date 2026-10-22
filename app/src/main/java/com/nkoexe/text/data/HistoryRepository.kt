package com.nkoexe.text.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class HistoryRecord(
    val text: String,
    val lastShownAtEpochMillis: Long
)

object HistoryPolicy {
    const val MAX_ENTRIES = 10

    fun record(current: List<HistoryRecord>, text: String, timestamp: Long): List<HistoryRecord> {
        if (text.isBlank()) return current.take(MAX_ENTRIES)
        return buildList {
            add(HistoryRecord(text, timestamp))
            current.asSequence().filterNot { it.text == text }
                .take(MAX_ENTRIES - 1).forEach(::add)
        }
    }
}

/** Small, private on-device database. No external storage or network access. */
class HistoryRepository(context: Context) {
    private val helper = HistoryDb(context.applicationContext)

    fun all(): List<HistoryRecord> = helper.readableDatabase.query(
        "history", arrayOf("text", "shown_at"), null, null, null, null, "shown_at DESC, rowid DESC"
    ).use { cursor ->
        buildList {
            while (cursor.moveToNext()) add(HistoryRecord(cursor.getString(0), cursor.getLong(1)))
        }
    }

    fun record(text: String, timestamp: Long = System.currentTimeMillis()) {
        if (text.isBlank()) return
        val db = helper.writableDatabase
        db.beginTransaction()
        try {
            db.delete("history", "text = ?", arrayOf(text))
            db.insertOrThrow("history", null, ContentValues().apply {
                put("text", text)
                put("shown_at", timestamp)
            })
            db.execSQL(
                "DELETE FROM history WHERE rowid NOT IN (SELECT rowid FROM history ORDER BY shown_at DESC, rowid DESC LIMIT ?)",
                arrayOf(HistoryPolicy.MAX_ENTRIES)
            )
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun delete(text: String) {
        helper.writableDatabase.delete("history", "text = ?", arrayOf(text))
    }

    private class HistoryDb(context: Context) : SQLiteOpenHelper(context, "text-history.db", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE history (text TEXT NOT NULL UNIQUE, shown_at INTEGER NOT NULL)")
            db.execSQL("CREATE INDEX history_shown_at ON history(shown_at DESC)")
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit
    }
}
