package com.example.remup.ui.note_widget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.remup.ui.add_edit_note_screen.AddEditNoteScreen
import com.example.remup.ui.theme.RemupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WidgetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent()
        {
            RemupTheme()
            {
                Log.d("#TAG", "onCreate: WidgetActivity")
                AddEditNoteScreen(id = -1,navController = rememberNavController())
            }
        }
    }
}