package com.randomthings.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.randomthings.presentation.favourite.FavouriteScreen
import com.randomthings.presentation.favourite.FavouriteViewModel
import com.randomthings.presentation.meme.MemeScreen
import com.randomthings.presentation.meme.MemeViewModel
import com.randomthings.presentation.random.RandomScreen
import com.randomthings.presentation.random.RandomThingViewModel


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Home.route,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = Home.route) {
            val viewModel: RandomThingViewModel = hiltViewModel(key = RandomThingViewModel.TAG)
            RandomScreen(modifier, viewModel)
        }
        composable(route = Meme.route) {
            val viewModel: MemeViewModel = hiltViewModel(key = MemeViewModel.TAG)
            MemeScreen(modifier, viewModel)
        }
        composable(route = Favourite.route) {
            val viewModel: FavouriteViewModel = hiltViewModel(key = FavouriteViewModel.TAG)
            FavouriteScreen(modifier, viewModel)
        }
    }
}
