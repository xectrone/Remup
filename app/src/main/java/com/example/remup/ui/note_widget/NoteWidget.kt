package com.example.remup.ui.note_widget

import android.content.Context
import androidx.compose.runtime.Composable
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
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.remup.MainActivity
import com.example.remup.R
import com.example.remup.data.database.AppDatabase
import com.example.remup.data.database.AppRepository


object NoteWidget: GlanceAppWidget() {
    val noteKey = stringPreferencesKey("note")
    @Composable
    override fun Content() {
        val note = currentState(key = noteKey)?:""
        Column(
            modifier = GlanceModifier
                .cornerRadius(20.dp)
                .background(imageProvider =ImageProvider(R.drawable.round_corner_rectangle))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        )
         {
            Text(
                modifier = GlanceModifier
                    .padding(16.dp)
                    .clickable(actionStartActivity<MainActivity>())
                ,
                text = note,
                style = TextStyle(
                    color = ColorProvider(R.color.xml_primary),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
             
            Button(text = "Refresh", onClick = actionRunCallback(RefreshCallback::class.java))

        }
    }


}

class RefreshCallback: ActionCallback{
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters){
        val repository = AppRepository.getInstance(AppDatabase.getInstance(context).dao)
        updateAppWidgetState(context, glanceId){
            val currentNote = it[NoteWidget.noteKey]
            if(currentNote != null)
                it[NoteWidget.noteKey] = "$currentNote yo "
            else
                it[NoteWidget.noteKey] = repository.firstNote().data
        }
        NoteWidget.update(context, glanceId)
    }
}

class NoteWidgetReceiver: GlanceAppWidgetReceiver(){
    override val glanceAppWidget: GlanceAppWidget
        get() = NoteWidget
}



