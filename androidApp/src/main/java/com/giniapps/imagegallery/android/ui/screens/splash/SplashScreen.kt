package com.giniapps.imagegallery.android.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.giniapps.imagegallery.android.ui.screens.Screen
import com.giniapps.imagegallery.view_models.SplashViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    continueToNextScreen: (route: String) -> Unit
) {
    val viewModel = getViewModel<SplashViewModel>()

    LaunchedEffect(true) {
        if (viewModel.isUserLoggedIn()) {
            continueToNextScreen(Screen.AlbumsScreen.route)
        } else
            continueToNextScreen(Screen.LoginScreen.route)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}