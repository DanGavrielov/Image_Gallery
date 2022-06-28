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

    override suspend fun getLoggedInUserDetails(userId: Long) =
        dataSource.getLoggedInUserDetails(userId) ?: User.emptyObject()

    override suspend fun getAlbumsForUserAndSaveToCache(userId: Long): List<Album> {
        val albums = dataSource.getAlbumsForUser(userId)
        cache.insertAlbums(albums)
        return albums
    }

    override suspend fun getAlbumsFromCache(userId: Long) =
        cache.getAlbums().ifEmpty {
            getAlbumsForUserAndSaveToCache(userId)
        }

    override suspend fun getPhotosForAlbumAndSaveToCache(albumId: Long): List<Photo> {
        val photos = dataSource.getPhotosForAlbum(albumId)
        cache.insertPhotos(photos)
        return photos
    }

    override suspend fun getPhotosForAlbumFromCache(albumId: Long) =
        cache.getPhotosForAlbumId(albumId).ifEmpty {
            getPhotosForAlbumAndSaveToCache(albumId)
        }

    override suspend fun getPhotoFromCacheById(photoId: Long) =
        cache.getPhotoById(photoId)

    override suspend fun login(userId: Long) {
        preferences.saveLoggedUserDetails(userId)
    }

    override suspend fun isUserLoggedIn() =
        preferences.getLoggedUserDetails() != -1L

    override suspend fun getLoggedInUserId() =
        preferences.getLoggedUserDetails()

    override suspend fun logout() {
        preferences.clearLoggedUser()
        cache.clearDatabase()
    }

}