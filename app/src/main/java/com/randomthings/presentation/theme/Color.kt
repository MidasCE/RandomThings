package com.randomthings.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val material3LightColors = lightColorScheme()
val material3DarkColors = darkColorScheme()

val ColorScheme.white: Color
    get() = Color(0xFFFFFFFF)

val ColorScheme.black: Color
    get() = Color(0xFF000000)