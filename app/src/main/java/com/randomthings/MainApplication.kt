package com.randomthings

import android.app.Application
import com.randomthings.initializers.CoilInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var coilInitializer: CoilInitializer

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch {
            coilInitializer.init()
        }
    }
}
