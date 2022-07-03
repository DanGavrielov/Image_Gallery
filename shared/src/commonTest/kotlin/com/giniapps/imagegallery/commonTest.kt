package com.giniapps.imagegallery

import com.giniapps.imagegallery.data.interfaces.Cache
import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.data.interfaces.DataSource
import com.giniapps.imagegallery.data.interfaces.Preferences
import com.giniapps.imagegallery.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CommonTest {
    private val repository = object: DataRepository {
            override val dataSource: DataSource
                get() = TODO("Not yet implemented")
            override val cache: Cache
                get() = TODO("Not yet implemented")
            override val preferences: Preferences
                get() = TODO("Not yet implemented")

            override suspend fun getUsers(): List<User> {
                return listOf(
                    testUser()
                )
            }

            override suspend fun getLoggedInUserDetails(userId: Long): User {
                TODO("Not yet implemented")
            }

            override suspend fun getAlbumsForUserAndSaveToCache(userId: Long): List<Album> {
                TODO("Not yet implemented")
            }

            override suspend fun getAlbumsFromCache(userId: Long): List<Album> {
                TODO("Not yet implemented")
            }

            override suspend fun getPhotosForAlbumAndSaveToCache(albumId: Long): List<Photo> {
                TODO("Not yet implemented")
            }

            override suspend fun getPhotosForAlbumFromCache(albumId: Long): List<Photo> {
                TODO("Not yet implemented")
            }

            override suspend fun getPhotoFromCacheById(photoId: Long): Photo {
                TODO("Not yet implemented")
            }

            override suspend fun login(userId: Long) {
                TODO("Not yet implemented")
            }

            override suspend fun isUserLoggedIn(): Boolean {
                TODO("Not yet implemented")
            }

            override suspend fun getLoggedInUserId(): Long {
                TODO("Not yet implemented")
            }

            override suspend fun logout() {
                TODO("Not yet implemented")
            }

        }
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Test
    fun dataLayer_testUserListFetching() {
        Dispatchers.setMain(mainThreadSurrogate)
        runBlocking(Dispatchers.Main) {
            val users = repository.getUsers()
            assertTrue { users.isNotEmpty() }
        }
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private fun testUser() =
        User(
            id = 0,
            name = "Dan",
            username = "Gambit",
            email = "dang@gini-apps.com",
            address = Address(
                street = "Street",
                suite = "Suite",
                city = "City",
                zipcode = "zipcode",
                geo = Geo("lat", "lng")
            ),
            phone = "050-something",
            website = "https://google.com",
            company = Company(
                name = "Company name",
                catchPhrase = "Some things are cool",
                bs = "BS"
            )
        )
}