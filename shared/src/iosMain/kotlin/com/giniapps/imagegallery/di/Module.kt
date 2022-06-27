package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.database.DatabaseDriverFactory
import org.koin.dsl.module
import platform.darwin.NSObject

actual fun platformModule() = module {
    factory { NSObject() }
    factory { DatabaseDriverFactory() }
}