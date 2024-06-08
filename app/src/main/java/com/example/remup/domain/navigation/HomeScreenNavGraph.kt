package com.example.remup.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.remup.ui.add_edit_note_screen.AddEditNoteScreen
import com.example.remup.ui.home_screen.HomeScreen
import com.example.remup.ui.theme.Constants.ID

@Composable
fun HomeScreenNavGraph(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Screen.Home.route )
    {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.AddEditNote.full,
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.IntType
                    defaultValue = -1
                })) {
            AddEditNoteScreen(navController = navController, id = it.arguments!!.getInt(ID))
        }

    }
}

