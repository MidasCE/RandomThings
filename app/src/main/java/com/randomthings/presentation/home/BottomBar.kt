package com.randomthings.presentation.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.randomthings.presentation.navigation.Destination
import com.randomthings.presentation.navigation.Home
import com.randomthings.presentation.navigation.bottomBarScreens

@Composable
fun BottomBar(
        navController: NavController
) {
    val tabs = remember { bottomBarScreens }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Home.route

    val currentScreen = bottomBarScreens.find { it.route == currentRoute } ?: Home


    BottomBarView(
        tabs = tabs,
        onTabSelected = { newScreen ->
            if (newScreen.route != currentRoute) {
                navController.navigate(newScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        currentScreen = currentScreen,
    )
}


@Composable
private fun BottomBarView(
    tabs: List<Destination>,
    onTabSelected: (Destination) -> Unit,
    currentScreen: Destination
) {
    NavigationBar(
        Modifier
            .windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            ),
        tonalElevation = 4.dp
    ) {
        tabs.forEach { tab ->
            val selected = currentScreen == tab
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = (if (selected) tab.selectedIcon else tab.unselectedIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                },
                label = {
                    Text(
                        text = stringResource(tab.stringTitleResource)
                    )
                },
                selected = selected,
                onClick = { onTabSelected(tab) },
                alwaysShowLabel = false,
                modifier = Modifier.navigationBarsPadding(),
            )
        }
    }
}