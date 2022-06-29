package com.giniapps.imagegallery.models

data class AlbumWithThumbnail(
    val id: Long,
    val userId: Long,
    val title: String,
    val albumThumbnailUrl: String
)