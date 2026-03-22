package com.randomthings.presentation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.randomthings.presentation.home.BottomBar
import com.randomthings.presentation.navigation.AppNavHost
import com.randomthings.presentation.theme.AppTheme

@Composable
fun RandomThingsApp() {
    AppTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.imePadding(),
            containerColor = Color.Transparent,
            bottomBar = { BottomBar(navController) },
        ) { innerPaddingModifier ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(bottom = innerPaddingModifier.calculateBottomPadding()),
            )
        }
    }
}