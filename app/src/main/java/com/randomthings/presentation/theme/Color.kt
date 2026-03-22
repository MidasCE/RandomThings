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

// Discovery (Home) screen
val HomePeachBackground = Color(0xFFFFC5A1)
val HomeGreetingOrange = Color(0xFFFF6B35)
val HomeFeaturedGradientStart = Color(0x00000000)
val HomeFeaturedGradientEnd = Color(0xCC5B2D8E)

// Meme screen
val MemeDarkBackground = Color(0xFF1A1A2E)
val MemeDarkCard = Color(0xFF232342)
val MemeDarkCardBack = Color(0xFF2A2A4A)
val MemeCardBackground = Color(0xFFFFFFFF)
val MemeLikeRed = Color(0xFFE53935)
val MemeRefreshPurple = Color(0xFF7C4DFF)

// My Collection (Favourite) screen
val CollectionBeigeBg = Color(0xFFF5ECD7)
val CollectionDarkBrown = Color(0xFF4A2C17)
val CollectionTabSelected = Color(0xFFB87333)

// Dad Joke Hub screen
val JokeSkyBlueLight = Color(0xFF87CEEB)
val JokeSkyBlueMid = Color(0xFF5DADE2)
val JokeSkyBlueDark = Color(0xFF2E86C1)
val JokeYellowCard = Color(0xFFFFC107)
val JokeYellowCardText = Color(0xFF3D2B00)
val JokeBubbleBlue = Color(0xFF2196F3)
val JokeBubbleOrange = Color(0xFFFF5722)
val JokeBubbleGreen = Color(0xFF43A047)
val JokeBubblePurple = Color(0xFF8E24AA)