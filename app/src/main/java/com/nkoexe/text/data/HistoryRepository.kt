package com.nkoexe.text.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * App-private persistence for the last ten displayed messages.
 *
 * Every write is transactional. The pure [HistoryPolicy] is the single source of
 * truth for duplicate handling, ordering, and the history limit.
 */
class HistoryRepository(context: Context) : AutoCloseable {
    private val helper = HistoryDb(context.applicationContext)

    @Synchronized
    fun all(): List<HistoryRecord> = readAll(helper.readableDatabase)

    @Synchronized
    fun record(text: String, timestamp: Long = System.currentTimeMillis()) {
        if (text.isBlank()) return

        val db = helper.writableDatabase
        db.beginTransaction()
        try {
            val updated = HistoryPolicy.record(readAll(db), text, timestamp)

            // History contains at most ten rows, so replacing it in one transaction
            // keeps the persistence layer simple and makes policy behavior explicit.
            db.delete(TABLE_HISTORY, null, null)
            // Insert backwards because rowid DESC is the stable, clock-independent order.
            updated.asReversed().forEach { item ->
                db.insertOrThrow(TABLE_HISTORY, null, ContentValues().apply {
                    put(COLUMN_TEXT, item.text)
                    put(COLUMN_SHOWN_AT, item.lastShownAtEpochMillis)
                })
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    @Synchronized
    fun delete(text: String) {
        helper.writableDatabase.delete(TABLE_HISTORY, "$COLUMN_TEXT = ?", arrayOf(text))
    }

    @Synchronized
    override fun close() = helper.close()

    private fun readAll(db: SQLiteDatabase): List<HistoryRecord> =
        db.query(
            TABLE_HISTORY,
            arrayOf(COLUMN_TEXT, COLUMN_SHOWN_AT),
            null,
            null,
            null,
            null,
            "rowid DESC"
        ).use { cursor ->
            buildList {
                while (cursor.moveToNext()) {
                    add(HistoryRecord(cursor.getString(0), cursor.getLong(1)))
                }
            }
        }

    private class HistoryDb(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE $TABLE_HISTORY (" +
                    "$COLUMN_TEXT TEXT NOT NULL UNIQUE, " +
                    "$COLUMN_SHOWN_AT INTEGER NOT NULL)"
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // No schema changes have shipped yet. Add explicit migrations when needed.
        }
    }

    private companion object {
        const val DATABASE_NAME = "text-history.db"
        const val DATABASE_VERSION = 1
        const val TABLE_HISTORY = "history"
        const val COLUMN_TEXT = "text"
        const val COLUMN_SHOWN_AT = "shown_at"
    }
}
