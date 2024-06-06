package com.example.remup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val data: String,
    val created: LocalDateTime = LocalDateTime.now()
)
{
    fun updateNote( newData: String = data, newId: Int = id, newCreated: LocalDateTime = created): Note {
        return Note(newId, newData, newCreated)
    }

    fun doesMatchSearchQuery(query: String) : Boolean{
        val matchingCombination = listOf("$data")
        return matchingCombination.any{it.contains(query, ignoreCase = true)}
    }

}

