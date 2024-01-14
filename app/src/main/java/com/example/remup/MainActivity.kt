package com.example.remup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.updateAll
import androidx.navigation.compose.rememberNavController
import com.example.remup.domain.broadcast.SystemBroadcastReceiver
import com.example.remup.domain.navigation.HomeScreenNavGraph
import com.example.remup.ui.note_widget.NoteWidget
import com.example.remup.ui.note_widget.NoteWidgetReceiver
import com.example.remup.ui.note_widget.RefreshCallback
import com.example.remup.ui.theme.RemupTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent()
        {
            RemupTheme()
            {
                val coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current
                SystemBroadcastReceiver(systemAction = Intent.ACTION_CONFIGURATION_CHANGED){
                    coroutineScope.launch {
                        NoteWidget().updateAll(context)
                    }
                }
                HomeScreenNavGraph(navController = rememberNavController())
            }
        }
    }
}