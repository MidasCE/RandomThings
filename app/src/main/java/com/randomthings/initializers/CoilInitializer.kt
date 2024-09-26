package com.randomthings.initializers

import coil.Coil
import coil.ImageLoader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoilInitializer @Inject constructor(
    private val imageLoader: ImageLoader
) : Initializer {
    override suspend fun init() {
        Coil.setImageLoader(imageLoader)
    }
}
