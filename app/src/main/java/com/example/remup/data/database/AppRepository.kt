package com.example.remup.data.database

import com.example.remup.data.model.Note

class AppRepository(private val appDao: AppDao){
    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(appDao: AppDao): AppRepository {
            return instance ?: synchronized(this) {
                instance ?: AppRepository(appDao).also { instance = it }
            }
        }
    }

    suspend fun addNote(note: Note) = appDao.addNote(note)
    suspend fun updateNote(note: Note) = appDao.updateNote(note)
    suspend fun deleteNote(note: Note) = appDao.deleteNote(note)
    fun noteList() = appDao.noteList()
    fun noteById(id: Int) = appDao.noteById(id)
    suspend fun firstNote() = appDao.firstNote()

}