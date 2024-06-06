package com.example.remup.ui.home_screen.search_app_bar

import com.example.remup.data.model.Note

data class NoteSelectionListItem(
    val note: Note,
    val isSelected: Boolean
)