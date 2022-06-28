package com.giniapps.imagegallery.data

import com.giniapps.imagegallery.AppDatabase
import com.giniapps.imagegallery.database.DatabaseDriverFactory
import com.giniapps.imagegallery.data.interfaces.Cache
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo

internal class LocalDatabaseWrapper(databaseDriverFactory: DatabaseDriverFactory): Cache {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val queries = database.appQueries

    override suspend fun insertAlbums(albums: List<Album>) {
        queries.transaction {
            albums.forEach {
                queries.insertAlbum(
                    userId = it.userId,
                    id = it.id,
                    title = it.title
                )
            }
        }
    }

    override suspend fun getAlbums() =
        queries.getAlbums { userId, id, title ->
            Album(userId, id, title)
        }.executeAsList()

    override suspend fun insertPhotos(photos: List<Photo>) {
        queries.transaction {
            photos.forEach {
                queries.insertPhoto(
                    albumId = it.albumId,
                    id = it.id,
                    title = it.title,
                    url = it.url,
                    thumbnailUrl = it.thumbnailUrl
                )
            }
        }
    }

    override suspend fun getPhotosForAlbumId(albumId: Long) =
        queries.getPhotosForAlbumId(albumId).executeAsList().map {
            Photo(
                albumId = it.albumId,
                id = it.id,
                title = it.title,
                url = it.url,
                thumbnailUrl = it.thumbnailUrl
            )
        }

    override suspend fun getPhotoById(photoId: Long): Photo {
        val result = queries.getPhotoById(photoId).executeAsOne()
        return Photo(
            albumId = result.albumId,
            id = result.id,
            title = result.title,
            url = result.url,
            thumbnailUrl = result.thumbnailUrl
        )
    }

    override suspend fun clearDatabase() {
        queries.transaction {
            queries.deleteAlbums()
            queries.deletePhotos()
        }
    }
}