package com.example.remup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.remup.domain.navigation.HomeScreenNavGraph
import com.example.remup.ui.theme.RemupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent()
        {
            RemupTheme()
            {
                HomeScreenNavGraph(navController = rememberNavController())

            }
        }
    }
}