package com.giniapps.imagegallery.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

actual abstract class SharedViewModel: ViewModel() {
    actual val scope = viewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}