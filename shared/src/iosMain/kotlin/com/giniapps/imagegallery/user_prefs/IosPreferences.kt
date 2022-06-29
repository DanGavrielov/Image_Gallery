package com.giniapps.imagegallery.user_prefs

import platform.Foundation.NSUserDefaults

private const val UserIdPrefKey = "user_id"

actual fun KMMContext.saveLoggedUserDetails(userId: Long) {
    NSUserDefaults.standardUserDefaults.setInteger(userId, UserIdPrefKey)
}

actual fun KMMContext.getLoggedUserDetails(): Long {
    val userId = NSUserDefaults.standardUserDefaults.integerForKey(UserIdPrefKey)
    return if (userId == 0L) -1 else userId
}