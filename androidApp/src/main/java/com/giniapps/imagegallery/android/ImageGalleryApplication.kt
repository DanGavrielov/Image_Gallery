package com.giniapps.imagegallery.android

import android.app.Application
import com.giniapps.imagegallery.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class ImageGalleryApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ImageGalleryApplication)
        }
    }
}