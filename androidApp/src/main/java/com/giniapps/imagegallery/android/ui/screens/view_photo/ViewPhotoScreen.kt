package com.giniapps.imagegallery.android.ui.screens.view_photo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giniapps.imagegallery.android.R
import com.giniapps.imagegallery.view_models.ViewPhotoViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ViewPhotoScreen(
    modifier: Modifier = Modifier,
    photoId: Long,
    navigateBack: () -> Unit
) {
    val viewModel = getViewModel<ViewPhotoViewModel>()
    val photo by viewModel.photo.collectAsState()

    LaunchedEffect(true) {
        viewModel.getPhoto(photoId)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back),
                    tint = Color.White
                )
            }

            if (photo.url.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.url)
                        .build(),
                    contentDescription = photo.title,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}