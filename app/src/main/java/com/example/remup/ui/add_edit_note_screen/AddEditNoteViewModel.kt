package com.example.remup.ui.add_edit_note_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remup.data.database.AppRepository
import com.example.remup.data.model.Note
import com.example.remup.ui.theme.Constants.ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var navNote = Note(0,"")

    private val _data = mutableStateOf<String>("")
    val data: State<String> = _data

    init {
        savedStateHandle.get<Int>(ID)?.let{
            if (it != -1) {
                viewModelScope.launch{
                    navNote = repository.noteById(it)
                    _data.value = navNote.data
                }
            }
        }

    }

    fun onDataChange(newData: String){
        _data.value = newData
    }

    fun onBackClick(id:Int){
        if(data.value != "") {
            navNote = navNote.updateNote(newData = data.value)
            viewModelScope.launch {
                if (id != -1)
                    repository.updateNote(navNote)
                else
                    repository.addNote(navNote)
            }
        }
    }

}