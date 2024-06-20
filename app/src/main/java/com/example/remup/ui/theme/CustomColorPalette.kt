package com.example.remup.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorPalette(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val tertiary: Color = Color.Unspecified,
    val quaternary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val backgroundSecondary: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val accent: Color = Color.Unspecified,
    val accentSecondary: Color = Color.Unspecified,


    )

val LocalCustomColorPalette = staticCompositionLocalOf { CustomColorPalette() }

val OnLightCustomColorPalette = CustomColorPalette(
    primary = Color(0xFF191919),
    secondary = Color(0xFF7F7F7F),
    tertiary = Color(0xFF828484),
    quaternary = Color(0xFFCDD0D0),
    background = Color(0xFFEDEDED),
    backgroundSecondary = Color(0xFFFFFFFF),
    surface = Color(0xFFF7F7F7),
    accent = Color(0xFF03A9F4),
    accentSecondary = Color(0xFF6E8CA0)

)

val OnDarkCustomColorPalette = CustomColorPalette(
    primary = Color(0xFFE9E9E9 ),
    secondary = Color(0xFF8C8C8C),
    tertiary = Color(0xFF828484),
    quaternary = Color(0xFF4F5151),
    background = Color(color = 0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    surface = Color(0xFF242424),
    accent = Color(0xFF03A9F4),
    accentSecondary = Color(0xFF6E8CA0)

)