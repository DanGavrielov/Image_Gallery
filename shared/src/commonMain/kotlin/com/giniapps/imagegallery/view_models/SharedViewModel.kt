package com.giniapps.imagegallery.view_models

import kotlinx.coroutines.CoroutineScope

expect abstract class SharedViewModel() {
    val coroutineScope: CoroutineScope

    protected open fun onCleared()
}