package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    suspend fun isUserLoggedIn(): Boolean {
        var loggedIn = false
        val job = coroutineScope.launch {
            loggedIn = repository.isUserLoggedIn()
        }
        job.join()
        return loggedIn
    }
}