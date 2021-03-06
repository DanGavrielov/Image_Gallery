package com.giniapps.imagegallery.data.interfaces

import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.models.User
import io.ktor.client.*

interface DataSource {
    val httpApiClient: HttpClient
    suspend fun getUsers(): List<User>
    suspend fun getLoggedInUserDetails(userId: Long): User
    suspend fun getAlbumsForUser(userId: Long): List<Album>
    suspend fun getPhotosForAlbum(albumId: Long): List<Photo>
}