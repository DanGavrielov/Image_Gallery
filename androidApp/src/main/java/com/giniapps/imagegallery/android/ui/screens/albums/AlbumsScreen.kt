package com.giniapps.imagegallery.android.ui.screens.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giniapps.imagegallery.android.R
import com.giniapps.imagegallery.android.ui.screens.shared_comps.UsernameScreenHeader
import com.giniapps.imagegallery.models.AlbumWithThumbnail
import com.giniapps.imagegallery.view_models.AlbumListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AlbumsScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    onAlbumClicked: (Long) -> Unit
) {
    val viewModel = getViewModel<AlbumListViewModel>()
    val loggedUser by viewModel.loggedUser.collectAsState()
    val albums by viewModel.albums.collectAsState()

    LaunchedEffect(true) {
        viewModel.initViewModel()
    }

    Column(modifier = modifier.padding(12.dp)) {
        UsernameScreenHeader(
            loggedUserName = loggedUser.name,
            onLogout = {
                viewModel.logout()
                onLogout()
            }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        if (albums.list.isEmpty()) {
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
                items(albums.list) {
                    AlbumItem(
                        album = it,
                        onAlbumClicked = onAlbumClicked
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    album: AlbumWithThumbnail,
    onAlbumClicked: (Long) -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onAlbumClicked(album.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(album.albumThumbnailUrl)
                .build(),
            contentDescription = album.title,
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = album.title,
            textAlign = TextAlign.Center
        )
    }
}