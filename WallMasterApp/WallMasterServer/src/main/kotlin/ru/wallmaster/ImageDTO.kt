package ru.wallmaster

import kotlinx.serialization.Serializable

@Serializable
data class ImageDTO(
    val imageId: Int,
    val group: String,
    val tags: String,
    val path: String
)