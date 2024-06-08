package com.example.remup.domain.navigation

import com.example.remup.ui.theme.Constants.ID

sealed class Screen(val route: String) {
    object Home: Screen("HOME_SCREEN")

    object AddEditNote: Screen("ADD_EDIT_NOTE_SCREEN"){
        val full = "$route?$ID={$ID}"
        fun navArg(id:Int = -1) = "$route?$ID=$id"
    }

}