package com.giniapps.imagegallery.data

import com.giniapps.imagegallery.data.interfaces.DataSource
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal class PlaceholderApiService(
    private val httpApiClient: HttpClient
) : DataSource {
//    private val httpApiClient: HttpClient =

    override suspend fun getUsers(): List<User> {
        return try {
            httpApiClient.get {
                url("https://jsonplaceholder.typicode.com/users")
            }.body()
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            emptyList()
        }
    }

    override suspend fun getAlbumsForUser(userId: Long): List<Album> {
        return try {
            httpApiClient.get {
                url("https://jsonplaceholder.typicode.com/albums?userId=$userId")
            }.body()
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            emptyList()
        }
    }

    override suspend fun getPhotosForAlbum(albumId: Long): List<Photo> {
        return try {
            httpApiClient.get {
                url("https://jsonplaceholder.typicode.com/photos?albumId=$albumId")
            }.body()
        } catch (ex: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ClientRequestException) {
            // 4xx - responses
            println("Error: ${ex.response.status.description}")
            emptyList()
        } catch (ex: ServerResponseException) {
            // 5xx - response
            println("Error: ${ex.response.status.description}")
            emptyList()
        }
    }

}