package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.database.DatabaseDriverFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    factory { androidApplication() }
    factory { DatabaseDriverFactory(androidContext()) }
}