package com.example.remup.domain.navigation

sealed class Screen(val route: String) {
    object Home: Screen("HOME_SCREEN")

    object AddEditNote: Screen("ADD_EDIT_NOTE_SCREEN?id={id}"){
        fun navArg(id:Int =  0) = "ADD_EDIT_NOTE_SCREEN?id=$id"
    }

    object ShowNote: Screen("SHOW_NOTE_SCREEN")


}