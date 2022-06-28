package com.giniapps.imagegallery.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giniapps.imagegallery.android.R
import com.giniapps.imagegallery.android.ui.screens.Screen
import com.giniapps.imagegallery.android.ui.screens.albums.AlbumItem
import com.giniapps.imagegallery.android.ui.screens.albums.AlbumsScreen
import com.giniapps.imagegallery.android.ui.screens.login.LoginScreen
import com.giniapps.imagegallery.android.ui.screens.photos.PhotoItem
import com.giniapps.imagegallery.android.ui.screens.photos.PhotosScreen
import com.giniapps.imagegallery.android.ui.screens.shared_comps.UsernameScreenHeader
import com.giniapps.imagegallery.android.ui.screens.splash.SplashScreen
import com.giniapps.imagegallery.android.ui.screens.view_photo.ViewPhotoScreen
import com.giniapps.imagegallery.android.ui.theme.ImageGalleryTheme
import com.giniapps.imagegallery.models.Album
import com.giniapps.imagegallery.models.AlbumWithThumbnail
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.view_models.*
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageGalleryTheme {
                App()
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
        ) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen {
                    navController.navigate(it) {
                        clearBackStack()
                    }
                }
            }
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(
                    modifier = Modifier.padding(top = 60.dp)
                ) {
                    navController.navigate(Screen.AlbumsScreen.route) {
                        clearBackStack()
                    }
                }
            }
            composable(route = Screen.AlbumsScreen.route) {
                AlbumsScreen(
                    onLogout = {
                        navController.navigate(Screen.SplashScreen.route) {
                            clearBackStack()
                        }
                    },
                    onAlbumClicked = {
                        navController.navigate("${Screen.PhotosScreen.route}/$it")
                    }
                )
            }
            composable(route = "${Screen.PhotosScreen.route}/{albumId}") {
                val albumId = it.arguments?.getString("albumId")
                albumId?.let { id ->
                    PhotosScreen(
                        albumId = id.toLong(),
                        onPhotoClicked = { photoId ->
                            navController.navigate("${Screen.ViewPhotoScreen.route}/$photoId")
                        },
                        navigateBack = { navController.popBackStack() },
                        onLogout = {
                            navController.navigate(Screen.SplashScreen.route) {
                                clearBackStack()
                            }
                        }
                    )
                }
            }
            composable(route = "${Screen.ViewPhotoScreen.route}/{photoId}") {
                val photoId = it.arguments?.getString("photoId")
                photoId?.let { id ->
                    ViewPhotoScreen(
                        photoId = id.toLong(),
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

fun NavOptionsBuilder.clearBackStack() {
    popUpTo(Screen.SplashScreen.route) {
        inclusive = true
    }
}