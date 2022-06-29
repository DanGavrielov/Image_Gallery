package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.AlbumWithThumbnail
import com.giniapps.imagegallery.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val loggedUser = MutableStateFlow(User.emptyObject()).asCommonFlow()
    val albums = MutableStateFlow(Albums(emptyList())).asCommonFlow()

    fun initViewModel() {
        coroutineScope.launch {
            val userId = repository.getLoggedInUserId()
            val albumData = Albums(
                createAlbumsWithThumbnails(userId)
            )
            val user = repository.getLoggedInUserDetails(userId)
            albums.emit(albumData)
            loggedUser.emit(user)
        }
    }

    private suspend fun createAlbumsWithThumbnails(userId: Long): List<AlbumWithThumbnail> {
        val albums = repository.getAlbumsFromCache(userId)
        return albums.map {
            AlbumWithThumbnail(
                id = it.id,
                userId = it.userId,
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

data class Albums(
    val list: List<AlbumWithThumbnail>
)