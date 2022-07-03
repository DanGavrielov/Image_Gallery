package com.giniapps.imagegallery.data.interfaces

interface Preferences {
    fun saveLoggedUserDetails(userId: Long)
    fun getLoggedUserDetails(): Long
    fun clearLoggedUser()
}