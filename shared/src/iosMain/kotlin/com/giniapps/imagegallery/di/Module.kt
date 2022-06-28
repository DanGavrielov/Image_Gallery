package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.data.interfaces.Preferences
import com.giniapps.imagegallery.database.DatabaseDriverFactory
import com.giniapps.imagegallery.user_prefs.UserPreferences
import org.koin.dsl.module
import platform.darwin.NSObject

actual fun platformModule() = module {
    factory<Preferences> {
        UserPreferences(
            context = NSObject()
        )
    }
    factory { DatabaseDriverFactory() }
}