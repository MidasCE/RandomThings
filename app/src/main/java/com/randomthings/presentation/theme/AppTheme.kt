package com.randomthings.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!dark) {
        material3LightColors
    } else {
        material3DarkColors
    }

    MaterialTheme(colorScheme = colors, content = content)
}
