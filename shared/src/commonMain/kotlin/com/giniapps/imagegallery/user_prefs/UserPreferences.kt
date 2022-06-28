package com.giniapps.imagegallery.user_prefs

import com.giniapps.imagegallery.data.interfaces.Preferences

class UserPreferences(private val context: KMMContext): Preferences {
    override fun saveLoggedUserDetails(userId: Long) {
        context.saveLoggedUserDetails(userId)
    }

    override fun getLoggedUserDetails() = context.getLoggedUserDetails()

    override fun clearLoggedUser() {
        saveLoggedUserDetails(userId = -1)
    }
}