package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.AlbumWithThumbnail
import com.giniapps.imagegallery.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val loggedUser = MutableStateFlow(User.emptyObject())
    val albums = MutableStateFlow(listOf<AlbumWithThumbnail>())

    init {
        coroutineScope.launch {
            val userId = repository.getLoggedInUserId()
            albums.emit(
                createAlbumsWithThumbnails(userId)
            )
            loggedUser.emit(
                repository.getLoggedInUserDetails(userId)
            )
        }
    }

    private suspend fun createAlbumsWithThumbnails(userId: Long): List<AlbumWithThumbnail> {
        val albums = repository.getAlbumsFromCache(userId)
        return albums.map {
            AlbumWithThumbnail(
                id = it.id,
                title = it.title,
                albumThumbnailUrl = repository.getPhotosForAlbumFromCache(it.id)[0].thumbnailUrl
            )
        }
    }

    fun logout() {
        coroutineScope.launch {
            repository.logout()
        }
    }
}