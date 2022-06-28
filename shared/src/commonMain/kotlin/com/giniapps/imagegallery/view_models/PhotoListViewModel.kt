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
    val loggedUser = MutableStateFlow(User.emptyObject())
    val album = MutableStateFlow(Album.emptyObject())
    val photos = MutableStateFlow(listOf<Photo>())

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
                repository.getPhotosForAlbumFromCache(albumId)
            )
        }

    fun logout() {
        coroutineScope.launch {
            repository.logout()
        }
    }
}