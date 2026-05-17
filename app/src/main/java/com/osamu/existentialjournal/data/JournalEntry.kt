package com.osamu.existentialjournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * يمثل تدوينة واحدة في مفكرة الوجود.
 */
@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val mood: String
)
