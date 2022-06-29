package com.giniapps.imagegallery.android.ui.screens.photos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giniapps.imagegallery.android.R
import com.giniapps.imagegallery.android.ui.screens.shared_comps.UsernameScreenHeader
import com.giniapps.imagegallery.models.Photo
import com.giniapps.imagegallery.view_models.PhotoListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PhotosScreen(
    modifier: Modifier = Modifier,
    albumId: Long,
    onPhotoClicked: (Long) -> Unit,
    navigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    val viewModel = getViewModel<PhotoListViewModel>()
    val loggedUser by viewModel.loggedUser.collectAsState()
    val album by viewModel.album.collectAsState()
    val photos by viewModel.photos.collectAsState()

    LaunchedEffect(true) {
        viewModel.getAlbum(albumId)
        viewModel.getPhotosForAlbum(albumId)
    }

    Column(modifier = modifier.padding(12.dp)) {
        UsernameScreenHeader(
            loggedUserName = loggedUser.name,
            onLogout = {
                viewModel.logout()
                onLogout()
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back)
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = album.title,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        if (photos.list.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(photos.list) {
                    PhotoItem(
                        photo = it,
                        onPhotoClicked = onPhotoClicked
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    photo: Photo,
    onPhotoClicked: (Long) -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onPhotoClicked(photo.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.thumbnailUrl)
                .build(),
            contentDescription = photo.title,
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = photo.title,
            textAlign = TextAlign.Center
        )
    }
}