package com.xectrone.remup.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xectrone.remup.ui.settings_screen.SettingsScreen
import com.xectrone.remup.ui.add_edit_note_screen.AddEditNoteScreen
import com.xectrone.remup.ui.home_screen.HomeScreen
import com.xectrone.remup.ui.theme.Constants.ID

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

        composable(route = Screen.Setting.route) {
            SettingsScreen(navController = navController)
        }

    }
}

