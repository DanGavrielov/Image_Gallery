package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.data.interfaces.Preferences
import com.giniapps.imagegallery.database.DatabaseDriverFactory
import com.giniapps.imagegallery.user_prefs.UserPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    factory<Preferences> {
        UserPreferences(
            context = androidApplication()
        )
    }
    factory { DatabaseDriverFactory(androidContext()) }
}