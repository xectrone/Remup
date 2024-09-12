package com.xectrone.remup.data

import android.content.Context
import android.net.Uri

object DataStore {
    fun saveSelectedSort(context: Context, sortOption: Int) {
        val prefs = context.getSharedPreferences("QuickMarkPrefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("selected_sort_option", sortOption).apply()
    }

    fun getSavedSort(context: Context): Int? {
        val prefs = context.getSharedPreferences("QuickMarkPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("selected_sort_option", 0)
    }
}