package com.example.remup.ui.note_widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.remup.MainActivity
import com.example.remup.R
import com.example.remup.ui.theme.Dimen
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
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
                    .padding(Dimen.Padding.p3)
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
                Row(
                    modifier = GlanceModifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = GlanceModifier
                            .padding(top = 5.dp, bottom = 10.dp, start = 12.dp, end = 12.dp),
                        text = "⟳",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = ColorProvider(R.color.xml_primary)
                        ),
                        onClick = actionRunCallback(RefreshCallback::class.java),
                        colors = ButtonDefaults.buttonColors(backgroundColor = ColorProvider(Color.LightGray.copy(alpha = 0.5f)))
                    )

                    Spacer(
                        modifier = GlanceModifier
                            .defaultWeight()
                    )

                    Button(
                        modifier = GlanceModifier,
                        text = "+",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = ColorProvider(R.color.xml_primary)
                        ),
                        onClick = actionStartActivity<WidgetActivity>(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = ColorProvider(Color.LightGray.copy(alpha = 0.5f)))
                    )
                }
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

