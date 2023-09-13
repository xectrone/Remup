package com.example.remup.data.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remup.data.database.AppRepository
import com.example.remup.data.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: AppRepository): ViewModel()
{
    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch( Dispatchers.IO ){repository.updateNote(note)}
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.deleteNote(note) }
    fun noteList() = repository.noteList()
    fun noteById(id: Int) = repository.noteById(id)

}
