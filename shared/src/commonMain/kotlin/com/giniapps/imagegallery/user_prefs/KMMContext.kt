package com.giniapps.imagegallery.user_prefs

expect class KMMContext

expect fun KMMContext.saveLoggedUserDetails(userId: Long)
expect fun KMMContext.getLoggedUserDetails(): Long