package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val loggedUser = MutableStateFlow(User.emptyObject()).asCommonFlow()
    val album = MutableStateFlow(Album.emptyObject()).asCommonFlow()
    val photos = MutableStateFlow(Photos(emptyList())).asCommonFlow()

    init {
        coroutineScope.launch {
            val userId = repository.getLoggedInUserId()
            loggedUser.emit(
                repository.getLoggedInUserDetails(userId)
            )
        }
    }

    fun getAlbum(albumId: Long) {
        coroutineScope.launch {
            val userId = repository.getLoggedInUserId()
            val albums = repository.getAlbumsFromCache(userId)
            album.emit( albums.first { it.id == albumId } )
        }
    }

    fun getPhotosForAlbum(albumId: Long) =
        coroutineScope.launch {
            photos.emit(
                Photos(repository.getPhotosForAlbumFromCache(albumId))
            )
        }

    fun logout() {
        coroutineScope.launch {
            repository.logout()
        }
    }
}

data class Photos(
    val list: List<Photo>
)