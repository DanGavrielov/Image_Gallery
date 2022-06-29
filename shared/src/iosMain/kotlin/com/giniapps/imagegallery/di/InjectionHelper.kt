package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.view_models.AlbumListViewModel
import com.giniapps.imagegallery.view_models.LoginViewModel
import com.giniapps.imagegallery.view_models.SplashViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object InjectionHelper: KoinComponent {
    val splashViewModel by inject<SplashViewModel>()
    val loginViewModel by inject<LoginViewModel>()
    val albumsViewModel by inject<AlbumListViewModel>()
}