package com.randomthings

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDispatcher @Inject constructor() {
    fun io() = Dispatchers.IO
    fun default() = Dispatchers.Default
    fun main() = Dispatchers.Main
    fun unconfined() = Dispatchers.Unconfined
}
