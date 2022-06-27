package com.giniapps.imagegallery.data

import com.giniapps.imagegallery.data.interfaces.Cache
import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.data.interfaces.DataSource
import com.giniapps.imagegallery.data.interfaces.Preferences
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.models.User

class PlaceholderDataRepository(
    override val dataSource: DataSource,
    override val cache: Cache,
    override val preferences: Preferences
) : DataRepository {
    override suspend fun getUsers() = dataSource.getUsers()

    override suspend fun getAlbumsForUser(userId: Long): List<Album> {
        val albums = dataSource.getAlbumsForUser(userId)
        cache.insertAlbums(albums)
        return albums
    }

    override suspend fun getAlbumsFromCache() =
        cache.getAlbums()

    override suspend fun getPhotosForAlbumAndSaveToCache(albumId: Long): List<Photo> {
        val photos = dataSource.getPhotosForAlbum(albumId)
        cache.insertPhotos(photos)
        return photos
    }

    override suspend fun getPhotosForAlbumFromCache(albumId: Long) =
        cache.getPhotosForAlbumId(albumId)

    override suspend fun getDataForUserAndSaveToCache(userId: Long) {
        val albums = getAlbumsForUser(userId)
        albums.forEach {
            getPhotosForAlbumAndSaveToCache(it.id)
        }
    }

    override suspend fun login(userId: Long) {
        preferences.saveLoggedUserDetails(userId)
        getDataForUserAndSaveToCache(userId)
    }

    override suspend fun isUserLoggedIn() =
        preferences.getLoggedUserDetails() > -1

    override suspend fun getLoggedInUserId() =
        preferences.getLoggedUserDetails()

    override suspend fun logout() {
        cache.clearDatabase()
    }

}