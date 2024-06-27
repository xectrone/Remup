package com.xectrone.remup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.xectrone.remup.ui.add_edit_note_screen.AddEditNoteScreen
import com.xectrone.remup.ui.theme.RemupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent()
        {
            RemupTheme()
            {
                AddEditNoteScreen(id = -1,navController = rememberNavController())
            }
        }
    }
}