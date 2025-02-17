package com.randomthings.presentation.base
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    protected fun launchNetwork(
        error: (Throwable) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                if (e is CancellationException) return@launch
                error(e)
            }
        }
    }
}