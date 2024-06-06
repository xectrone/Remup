//package com.example.remup.ui.show_note_screen
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.remup.data.database.AppRepository
//import com.example.remup.data.model.Note
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ShowNoteViewModel @Inject constructor(private val repository: AppRepository): ViewModel()
//{
//    private val _noteList = mutableStateOf<List<Note>>(emptyList())
//    val noteList: State<List<Note>> = _noteList
//
//    fun getNoteList(){
//        viewModelScope.launch {
//            repository.noteList().collect{
//                _noteList.value = it
//            }
//        }
//    }
//
//
//}