package com.xectrone.remup.ui.home_screen
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xectrone.remup.data.database.AppRepository
import com.xectrone.remup.ui.home_screen.search_app_bar.NoteSelectionListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository): ViewModel()
{
    private val _noteList = MutableStateFlow<List<NoteSelectionListItem>>(emptyList())
    val noteList: StateFlow<List<NoteSelectionListItem>> = _noteList

    private val _originalNoteList = MutableStateFlow<List<NoteSelectionListItem>>(emptyList())

    private val _selectionMode = mutableStateOf(false)
    val selectionMode: State<Boolean> = _selectionMode

    fun getFlashcardList()
    {
        viewModelScope.launch {
            repository.noteList().collect{
                _noteList.value = it.map { note ->  NoteSelectionListItem(note = note, isSelected = false) }
                _originalNoteList.value =  _noteList.value
            }
        }
    }

    fun onItemClick(item: NoteSelectionListItem){
        _noteList.value = noteList.value.map { if (it==item) it.copy(isSelected = !it.isSelected) else it }
        if (_noteList.value.none { it.isSelected })
            _selectionMode.value = false
    }
    fun onItemLongClick(item: NoteSelectionListItem){
        if (!selectionMode.value) {
            _selectionMode.value = true
            _noteList.value = noteList.value.map { if (it==item) it.copy(isSelected = !it.isSelected) else it }
        }
    }

    fun onSearch(text:String){
        _noteList.value = _originalNoteList.value.filter {
            it.note.doesMatchSearchQuery(text)
        }
        if (_noteList.value.none { it.isSelected })
            _selectionMode.value = false
    }

    fun onSearchClose(){
        _noteList.value = _originalNoteList.value
        _selectionMode.value = false
    }

    fun onDelete()
    {
        viewModelScope.launch {
            noteList.value.filter { it.isSelected }.forEach { item -> repository.deleteNote(item.note) }
            _selectionMode.value = false
        }
    }

    fun onSerchClose(){
        _noteList.value = _originalNoteList.value
        _selectionMode.value = false
    }
}