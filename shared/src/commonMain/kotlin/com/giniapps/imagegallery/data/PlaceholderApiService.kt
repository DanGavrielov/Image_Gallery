package com.giniapps.imagegallery.data

import com.giniapps.imagegallery.data.interfaces.DataSource
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

internal class PlaceholderApiService(
    private val httpApiClient: HttpClient
) : DataSource {
    override suspend fun getUsers(): List<User> {
        return makeRequest(
            request = {
                httpApiClient.get {
                    url("https://jsonplaceholder.typicode.com/users")
                }.body()
            },
            returnOnFailure = emptyList()
        )
    }

    override suspend fun getLoggedInUserDetails(userId: Long): User {
        return makeRequest(
            request = {
                httpApiClient.get {
                    url("https://jsonplaceholder.typicode.com/users/$userId")
                }.body()
            },
            returnOnFailure = User.emptyObject()
        )
    }

    override suspend fun getAlbumsForUser(userId: Long): List<Album> {
        return makeRequest(
            request = {
                httpApiClient.get {
                    url("https://jsonplaceholder.typicode.com/albums?userId=$userId")
                }.body()
            },
            returnOnFailure = emptyList()
        )
    }

    override suspend fun getPhotosForAlbum(albumId: Long): List<Photo> {
        return makeRequest(
            request = {
                httpApiClient.get {
                    url("https://jsonplaceholder.typicode.com/photos?albumId=$albumId")
                }.body()
            },
            returnOnFailure = emptyList()
        )
    }

    private suspend fun <T> makeRequest(request: suspend () -> T, returnOnFailure: T): T {
        return try {
            request()
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            returnOnFailure
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            returnOnFailure
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            returnOnFailure
        }
    }
}