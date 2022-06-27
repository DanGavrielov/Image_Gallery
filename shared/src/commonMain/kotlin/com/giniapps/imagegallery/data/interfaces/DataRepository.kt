package com.giniapps.imagegallery.data.interfaces

import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.models.User

interface DataRepository {
    val dataSource: DataSource
    val cache: Cache
    val preferences: Preferences
    suspend fun getUsers(): List<User>
    suspend fun getAlbumsForUser(userId: Long): List<Album>
    suspend fun getAlbumsFromCache(): List<Album>
    suspend fun getPhotosForAlbumAndSaveToCache(albumId: Long): List<Photo>
    suspend fun getPhotosForAlbumFromCache(albumId: Long): List<Photo>
    suspend fun getDataForUserAndSaveToCache(userId: Long)
    suspend fun login(user: User)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getLoggedInUserId(): Long
    suspend fun logout()
}