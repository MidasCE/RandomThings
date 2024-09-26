package com.randomthings.presentation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.randomthings.presentation.home.BottomBar
import com.randomthings.presentation.random.RandomScreen
import com.randomthings.presentation.random.RandomThingViewModel

@Composable
fun RandomThingsApp() {
    Scaffold(
        modifier = Modifier.imePadding(),
        bottomBar = { BottomBar() },
    ) { innerPaddingModifier ->
        val viewModel: RandomThingViewModel = hiltViewModel(key = RandomThingViewModel.TAG)
        RandomScreen(Modifier.padding(innerPaddingModifier), viewModel)
    }
}