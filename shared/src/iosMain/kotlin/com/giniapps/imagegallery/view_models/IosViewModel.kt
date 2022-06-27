package com.giniapps.imagegallery.view_models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

actual abstract class SharedViewModel {
    private var hasCleared = false
    actual val scope: CoroutineScope by lazy {
        val result = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main)

        if (hasCleared)
            closeWithRuntimeException(result)

        return@lazy result
    }

    protected actual open fun onCleared() {}

    fun clear() {
        hasCleared = true
        closeWithRuntimeException(scope)
        onCleared()
    }

    companion object {
        private fun closeWithRuntimeException(obj: Any?) {
            if (obj is Closeable) {
                try {
                    obj.close()
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
        }
    }
}

class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }

    companion object
}

interface Closeable {
    fun close()
}