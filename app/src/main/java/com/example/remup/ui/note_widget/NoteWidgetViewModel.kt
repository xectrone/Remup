package com.example.remup.ui.note_widget

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remup.data.database.AppRepository
import com.example.remup.data.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class NoteWidgetViewModel @Inject constructor(private val repository:AppRepository): ViewModel()
{
    private val _note = mutableStateOf<String>("")
    val note: State<String> = _note


    fun getNote() {
        viewModelScope.launch(Dispatchers.IO) {
            _note.value = repository.firstNote().data
        }
    }

}