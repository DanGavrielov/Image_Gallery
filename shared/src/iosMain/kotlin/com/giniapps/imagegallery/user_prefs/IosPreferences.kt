package com.giniapps.imagegallery.user_prefs

import platform.Foundation.NSUserDefaults

private const val UserIdPrefKey = "user_id"

actual fun KMMContext.saveLoggedUserDetails(userId: Long) {
    NSUserDefaults.standardUserDefaults.setInteger(userId, UserIdPrefKey)
}

actual fun KMMContext.getLoggedUserDetails(): Long {
    return NSUserDefaults.standardUserDefaults.integerForKey(UserIdPrefKey)
}