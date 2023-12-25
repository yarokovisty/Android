package ru.wallmaster

import kotlinx.serialization.Serializable

@Serializable
data class SearchReceiveRemote(
    val tag: String
)

@Serializable
data class SearchResponseRemote(
    val tags: String,
    val img: ByteArray
)