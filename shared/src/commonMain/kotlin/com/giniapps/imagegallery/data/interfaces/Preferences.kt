package com.giniapps.imagegallery.data.interfaces

import com.giniapps.imagegallery.user_prefs.getLoggedUserDetails
import com.giniapps.imagegallery.user_prefs.saveLoggedUserDetails

interface Preferences {
    fun saveLoggedUserDetails(userId: Long)
    fun getLoggedUserDetails(): Long
}