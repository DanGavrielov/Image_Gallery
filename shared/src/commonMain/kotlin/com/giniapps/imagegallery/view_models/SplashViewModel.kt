package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val isUserLoggedInState = MutableStateFlow(false)

    init {
        coroutineScope.launch {
            isUserLoggedInState.emit(
                repository.isUserLoggedIn()
            )
        }
    }
}