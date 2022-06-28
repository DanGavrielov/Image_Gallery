package com.giniapps.imagegallery.models

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val userId: Long,
    val id: Long,
    val title: String
) {
    companion object {
        fun emptyObject() =
            Album(
                userId = -1,
                id = -1,
                title = ""
            )
    }
}