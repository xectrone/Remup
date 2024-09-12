package com.xectrone.remup.ui.home_screen
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xectrone.remup.data.DataStore.getSavedSort
import com.xectrone.remup.data.DataStore.saveSelectedSort
import com.xectrone.remup.data.database.AppRepository
import com.xectrone.remup.ui.home_screen.search_app_bar.NoteSelectionListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository, private val application: Application): ViewModel()
{
    private val _noteList = MutableStateFlow<List<NoteSelectionListItem>>(emptyList())
    val noteList: StateFlow<List<NoteSelectionListItem>> = _noteList

    private val _originalNoteList = MutableStateFlow<List<NoteSelectionListItem>>(emptyList())

    private val _sortOption = mutableStateOf<Int?>(0)
    val sortOption: State<Int?> = _sortOption

    private val _selectionMode = mutableStateOf(false)
    val selectionMode: State<Boolean> = _selectionMode

    private val _isExpanded = mutableStateOf<Boolean>(false)
    val isExpanded: State<Boolean> = _isExpanded

    init {
        observeSortOption()
        getFlashcardList()
    }

    fun getFlashcardList()
    {
        viewModelScope.launch {
            repository.noteList().collect{
                _noteList.value = when(sortOption.value){
                    SortOptions.contentASC -> it.map { note ->  NoteSelectionListItem(note = note, isSelected = false) }.sortedBy { note -> note.note.data }
                    SortOptions.contentDESC -> it.map { note ->  NoteSelectionListItem(note = note, isSelected = false) }.sortedBy { note -> note.note.data }.reversed()
                    SortOptions.lastModifiedASC -> it.map { note ->  NoteSelectionListItem(note = note, isSelected = false) }.sortedBy { note -> note.note.created }
                    SortOptions.lastModifiedDESC -> it.map { note ->  NoteSelectionListItem(note = note, isSelected = false) }.sortedBy { note -> note.note.created }.reversed()
                    else -> it.map { note ->  NoteSelectionListItem(note = note, isSelected = false) }.sortedBy { note -> note.note.created }.reversed()
                }

                _originalNoteList.value =  _noteList.value
            }
        }
    }

    fun observeSortOption() {
        viewModelScope.launch {
            getSavedSort(application)?.let{
                if(sortOption.value != it) {
                    _sortOption.value = it
                    getFlashcardList()
                }
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

    fun onSort(sortOption:Int){
        viewModelScope.launch {
            saveSelectedSort(context = application, sortOption = sortOption)
        }
        observeSortOption()
        hideMenu()
    }

    fun showMenu(){
        _isExpanded.value = true
    }

    fun hideMenu(){
        _isExpanded.value = false
    }

    fun onClear(){
        if (selectionMode.value) {
            _selectionMode.value = false
            _noteList.value = noteList.value.map { it.copy(isSelected = false)}
        }
    }
}