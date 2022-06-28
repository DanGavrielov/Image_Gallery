package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ViewPhotoViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val photo = MutableStateFlow(Photo.emptyObject())

    fun getPhoto(photoId: Long) {
        coroutineScope.launch {
            photo.emit(
                repository.getPhotoFromCacheById(photoId)
            )
        }
    }
}