package com.giniapps.imagegallery.models

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val userId: Long,
    val id: Long,
    val title: String
)