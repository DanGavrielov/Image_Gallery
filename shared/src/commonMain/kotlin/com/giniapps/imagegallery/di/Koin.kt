package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.data.LocalDatabaseWrapper
import com.giniapps.imagegallery.data.PlaceholderApiService
import com.giniapps.imagegallery.data.PlaceholderDataRepository
import com.giniapps.imagegallery.data.interfaces.Cache
import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.data.interfaces.DataSource
import com.giniapps.imagegallery.data.interfaces.Preferences
import com.giniapps.imagegallery.user_prefs.UserPreferences
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = { }) {
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            platformModule()
        )
    }
}

fun initKoin() = initKoin { }

val repositoryModule = module {
    factory {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    factory<DataSource> {
        PlaceholderApiService(
            httpApiClient = get()
        )
    }

    factory<Cache> {
        LocalDatabaseWrapper(
            databaseDriverFactory = get()
        )
    }

    factory<Preferences> {
        UserPreferences(
            context = get()
        )
    }

    factory<DataRepository> {
        PlaceholderDataRepository(
            dataSource = get(),
            cache = get(),
            preferences = get()
        )
    }
}