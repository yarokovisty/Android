package ru.wallmaster

import kotlinx.serialization.Serializable

@Serializable
data class GroupDTO(
    val groupId: Int,
    val name: String,
    val path: String
)