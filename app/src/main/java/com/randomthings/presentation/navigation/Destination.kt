package com.randomthings.presentation.navigation

/**
 * Contract for information needed on every navigation destination
 */
interface Destination {
    val route: String
}

object Home : Destination {
    override val route = "home"
}
