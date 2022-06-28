package com.giniapps.imagegallery.user_prefs

import android.content.Context
import android.content.SharedPreferences

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

private fun KMMContext.getSharedPrefs(): SharedPreferences {
    val context = this
    return context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
}