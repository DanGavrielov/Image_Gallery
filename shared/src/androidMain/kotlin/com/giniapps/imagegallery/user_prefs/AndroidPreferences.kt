package com.giniapps.imagegallery.user_prefs

import android.content.Context

private const val PrefName = "prefs"
private const val UserIdPrefKey = "user_id"

actual fun KMMContext.saveLoggedUserDetails(userId: Long) {
    getSharedPrefs()
        .edit()
        .putLong(UserIdPrefKey, userId)
        .apply()
}

actual fun KMMContext.getLoggedUserDetails(): Long {
    return getSharedPrefs()
        .getLong(UserIdPrefKey, -1)
}

private fun KMMContext.getSharedPrefs() = getSharedPreferences(PrefName, Context.MODE_PRIVATE)