package com.xectrone.remup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.xectrone.remup.domain.navigation.HomeScreenNavGraph
import com.xectrone.remup.ui.theme.RemupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Remup)
        setContent()
        {
            RemupTheme()
            {
                HomeScreenNavGraph(navController = rememberAnimatedNavController())

            }
        }
    }
}