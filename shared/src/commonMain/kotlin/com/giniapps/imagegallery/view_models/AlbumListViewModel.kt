package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val albums = MutableStateFlow(listOf<Album>())

    init {
        coroutineScope.launch {
            val userId = repository.getLoggedInUserId()
            albums.emit(
                repository.getAlbumsFromCache(userId)
            )
        }
    }
}