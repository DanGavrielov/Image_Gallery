package com.giniapps.imagegallery.view_models

import kotlinx.coroutines.CoroutineScope

expect abstract class SharedViewModel() {
    val scope: CoroutineScope

    protected open fun onCleared()
}