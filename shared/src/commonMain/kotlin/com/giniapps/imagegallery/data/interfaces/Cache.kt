package com.giniapps.imagegallery.data.interfaces

import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo

interface Cache {
    suspend fun insertAlbums(albums: List<Album>)
    suspend fun getAlbums(): List<Album>
    suspend fun insertPhotos(photos: List<Photo>)
    suspend fun getPhotosForAlbumId(albumId: Long): List<Photo>
    suspend fun clearDatabase()
}