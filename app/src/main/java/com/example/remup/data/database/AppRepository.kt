package com.example.remup.data.database

import com.example.remup.data.model.Note

class AppRepository(private val appDao: AppDao){

    suspend fun addNote(note: Note) = appDao.addNote(note)
    suspend fun updateNote(note: Note) = appDao.updateNote(note)
    suspend fun deleteNote(note: Note) = appDao.deleteNote(note)
    fun noteList() = appDao.noteList()

    suspend fun noteById(id: Int) = appDao.noteById(id)
    suspend fun randomNote() = appDao.randomNote()


}