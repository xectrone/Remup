package com.example.remup.ui.note_widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import com.example.remup.R
import com.example.remup.AddNoteActivity
import com.example.remup.data.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.d("NoteWidgetProvider", "onUpdate called")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        scheduleUpdate(context)
    }

    override fun onEnabled(context: Context) {
        Log.d("NoteWidgetProvider", "onEnabled called")
        scheduleUpdate(context)
    }

    override fun onDisabled(context: Context) {
        Log.d("NoteWidgetProvider", "onDisabled called")
        cancelUpdate(context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == ACTION_WIDGET_UPDATE) {
            context?.let {
                val appWidgetManager = AppWidgetManager.getInstance(it)
                val componentName = ComponentName(it, NoteWidgetProvider::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
                onUpdate(it, appWidgetManager, appWidgetIds)
            }
        }
    }

    companion object {
        private const val UPDATE_INTERVAL_MILLIS = 60 * 60 * 1000L // 1 minute
        private const val ACTION_WIDGET_UPDATE = "com.example.remup.ACTION_WIDGET_UPDATE"
        private const val TAG = "NoteWidgetProvider"

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            Log.d(TAG, "updateAppWidget called")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val db = AppDatabase.getInstance(context)
                    val note = db.dao.randomNote()
                    val randomNote = note?.data ?: "No notes available"
                    Log.d(TAG, "Random Note: $randomNote")

                    val views = RemoteViews(context.packageName, R.layout.widget_layout)
                    views.setTextViewText(R.id.widget_note_text, randomNote)

                    // Set up button click handler
                    val intent = Intent(context, NoteWidgetProvider::class.java).apply {
                        action = ACTION_WIDGET_UPDATE
                    }
                    val pendingIntent = PendingIntent.getBroadcast(
                        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    views.setOnClickPendingIntent(R.id.update_widget_button, pendingIntent)

                    // Set up button click handler for opening the main activity
                    val openMainIntent = Intent(context, AddNoteActivity::class.java)
                    val openMainPendingIntent = PendingIntent.getActivity(
                        context, 0, openMainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    views.setOnClickPendingIntent(R.id.add_note_widget_button, openMainPendingIntent)

                    appWidgetManager.updateAppWidget(appWidgetId, views)
                    Log.d(TAG, "Widget updated with new note")
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching random note", e)
                }
            }
        }

        private fun scheduleUpdate(context: Context) {
            val intent = Intent(context, NoteWidgetProvider::class.java).apply {
                action = ACTION_WIDGET_UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val triggerAtMillis = SystemClock.elapsedRealtime() + UPDATE_INTERVAL_MILLIS
            Log.d(TAG, "Scheduling update in $UPDATE_INTERVAL_MILLIS milliseconds")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent
                )
            } else {
                alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, UPDATE_INTERVAL_MILLIS, pendingIntent
                )
            }
            Log.d(TAG, "Update scheduled")
        }

        private fun cancelUpdate(context: Context) {
            val intent = Intent(context, NoteWidgetProvider::class.java).apply {
                action = ACTION_WIDGET_UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            Log.d(TAG, "Update cancelled")
        }
    }
}