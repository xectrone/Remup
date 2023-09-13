package com.example.remup.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.remup.ui.add_edit_note_screen.AddEditNoteScreen
import com.example.remup.ui.home_screen.HomeScreen
import com.example.remup.ui.show_note_screen.ShowNoteScreen

@Composable
fun HomeScreenNavGraph(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screen.Home.route )
    {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.AddEditNote.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                })) {
            AddEditNoteScreen(navController = navController, id = it.arguments!!.getInt("id"))
        }

        composable(route = Screen.ShowNote.route) {
            ShowNoteScreen(navController = navController)
        }
    }
}

