package com.giniapps.imagegallery.models

import kotlinx.serialization.Serializable

@Serializable
data class Photo (
    val albumId: Long,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)