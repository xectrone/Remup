package com.example.remup.ui.settings_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.remup.ui.settings_screen.backup.LocalBackupScreenSection
import com.example.remup.ui.theme.Constants
import com.example.remup.ui.theme.CustomTypography
import com.example.remup.ui.theme.Dimen
import com.example.remup.ui.theme.LocalCustomColorPalette

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = LocalCustomColorPalette.current.background,

        //region - Top Bar -
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = Dimen.Padding.statusBar),
                backgroundColor = LocalCustomColorPalette.current.background,
                contentColor = LocalCustomColorPalette.current.primary,
                title = { Text(
                    text = "Settings",
                    style = CustomTypography.h2,
                    color = LocalCustomColorPalette.current.primary

                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = Constants.Labels.BACK)
                    }
                },
                elevation = Dimen.TopBar.elevation
            )
        }
        //endregion
    ) {
        Column(
        ) {
            LocalBackupScreenSection()
        }
    }
}