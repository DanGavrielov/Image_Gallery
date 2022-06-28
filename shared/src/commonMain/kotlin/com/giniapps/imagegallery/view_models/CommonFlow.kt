package com.giniapps.imagegallery.view_models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> MutableStateFlow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

class CommonFlow<T>(private val origin: MutableStateFlow<T>) : MutableStateFlow<T> by origin {
    fun watch(block: (T) -> Unit): Cancellable {
        val job = Job()

        onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main + job))

        return object : Cancellable {
            override fun cancel() {
                job.cancel()
            }
        }
    }
}

interface Cancellable {
    fun cancel()
}