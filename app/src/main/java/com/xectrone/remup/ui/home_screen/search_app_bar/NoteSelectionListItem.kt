package com.xectrone.remup.ui.home_screen.search_app_bar

import com.xectrone.remup.data.model.Note

data class NoteSelectionListItem(
    val note: Note,
    val isSelected: Boolean
)