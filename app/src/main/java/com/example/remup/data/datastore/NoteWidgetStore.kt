package com.example.remup.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NoteWidgetStore(private val context: Context)  {
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("NoteWidget")
        private val WIDGET_TEXT_KEY = stringPreferencesKey("widget_text")
    }

    suspend fun readText(): String {
        val preferences = context.dataStore.data.first()
        return preferences[WIDGET_TEXT_KEY] ?:""
    }

    suspend fun saveText(newText: String){
        context.dataStore.edit {
            it[WIDGET_TEXT_KEY] = newText
        }
    }

    val getWidgetText: Flow<String?> = context.dataStore.data.map { it[WIDGET_TEXT_KEY] }

    suspend fun saveWidgetText(newWidgetText: String){
        context.dataStore.edit { it[WIDGET_TEXT_KEY] = newWidgetText }
    }
}


