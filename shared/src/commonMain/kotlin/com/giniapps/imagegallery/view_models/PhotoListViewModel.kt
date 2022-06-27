package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val photos = MutableStateFlow(listOf<Photo>())

    fun getPhotosForAlbum(albumId: Long) =
        coroutineScope.launch {
            photos.emit(
                repository.getPhotosForAlbumFromCache(albumId)
            )
        }
}