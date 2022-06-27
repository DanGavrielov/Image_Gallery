package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.database.DatabaseDriverFactory
import com.giniapps.imagegallery.user_prefs.KMMContext
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    factory { KMMContext() }
    factory { DatabaseDriverFactory(androidContext()) }
}