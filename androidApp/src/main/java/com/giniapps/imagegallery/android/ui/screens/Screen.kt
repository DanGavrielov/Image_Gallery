package com.giniapps.imagegallery.android.ui.screens

sealed class Screen(val route: String) {
    object SplashScreen: Screen(route = "splash")
    object LoginScreen: Screen(route = "login")
    object AlbumsScreen: Screen(route = "albums")
    object PhotosScreen: Screen(route = "photos")
    object ViewPhotoScreen: Screen(route = "view_photo")
}