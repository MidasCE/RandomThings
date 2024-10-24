package com.randomthings.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.randomthings.R

/**
 * Contract for information needed on every navigation destination
 */
interface Destination {
    val route: String
    @get:StringRes
    val stringTitleResource: Int
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

object Home : Destination {
    override val route = "home"
    override val stringTitleResource: Int
        get() = R.string.bottom_navigation_home
    override val selectedIcon = Icons.Filled.Home
    override val unselectedIcon = Icons.Outlined.Home
}

object Meme : Destination {
    override val route = "meme"
    override val stringTitleResource: Int
        get() = R.string.bottom_navigation_meme
    override val selectedIcon = Icons.Filled.ChatBubble
    override val unselectedIcon = Icons.Outlined.ChatBubble
}

object Favourite : Destination {
    override val route = "favourite"
    override val stringTitleResource: Int
        get() = R.string.bottom_navigation_favourite
    override val selectedIcon = Icons.Filled.Favorite
    override val unselectedIcon = Icons.Outlined.Favorite
}

// Screens to be displayed in the Bottom Bar
val bottomBarScreens = listOf(Home, Meme, Favourite)
