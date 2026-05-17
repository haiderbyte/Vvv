package com.osamu.existentialjournal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * واجهة الوصول للبيانات لجدول التدوينات.
 */
@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<JournalEntry>>

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): JournalEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntry)

    @Delete
    suspend fun deleteEntry(entry: JournalEntry)

    @Update
    suspend fun updateEntry(entry: JournalEntry)
}
