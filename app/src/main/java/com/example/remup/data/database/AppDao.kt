package com.example.remup.data.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.remup.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY created DESC")
    fun noteList(): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun noteById(id: Int): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = 1")
    suspend fun firstNote():Note

}