package com.example.remup.ui.note_widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
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
import com.example.remup.domain.broadcast.SystemBroadcastReceiver
import com.example.remup.ui.theme.Dimen
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

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val viewModel = EntryPoints.get(context, NoteWidgetEntryPoint::class.java).getViewModel()
        val note by viewModel.note
        viewModel.getNote()

        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(imageProvider = ImageProvider(R.drawable.round_corner_rectangle)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(modifier = GlanceModifier
                    .padding(Dimen.Padding.p3),
                    text = note,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = ColorProvider(R.color.xml_primary)
                    )
                )
                Button(
                    modifier = GlanceModifier
                        .padding(top = 0.dp, bottom = 6.dp, start = 7.dp, end = 7.dp),
                    text = "⟳",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    ),
                    onClick = actionRunCallback(RefreshCallback::class.java),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ColorProvider(Color.Black.copy(alpha = 0.07f)))

                )
            }
        }
    }
}
class RefreshCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Log.d("#TAG", "onAction: ")
        NoteWidget().update(context, glanceId)
    }
}

