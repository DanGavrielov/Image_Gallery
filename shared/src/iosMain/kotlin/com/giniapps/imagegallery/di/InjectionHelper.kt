package com.giniapps.imagegallery.di

import com.giniapps.imagegallery.view_models.LoginViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object InjectionHelper: KoinComponent {
    val loginViewModel by inject<LoginViewModel>()
}