package com.example.remup.ui.note_widget

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.remup.MainActivity
import com.example.remup.R
import com.example.remup.data.database.AppDatabase
import com.example.remup.data.database.AppRepository
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent

class NoteWidget : GlanceAppWidget() {
    //region - Entry Point -
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface NoteWidgetEntryPoint{
        fun getViewModel():NoteWidgetViewModel
    }
    //endregion

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val viewModel = EntryPoints.get(context, NoteWidgetEntryPoint::class.java).getViewModel()
        val note by viewModel.note
        viewModel.getNote()

        provideContent {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                Text(
                    text = note,
                )
            }
        }
    }
}

