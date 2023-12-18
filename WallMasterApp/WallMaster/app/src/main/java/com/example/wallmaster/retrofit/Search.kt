package com.example.wallmaster.retrofit

data class SearchRequestRemote(
    val tag: String
)

data class SearchReceiveRemote(
    val tags: String,
    val img: ByteArray
)

data class ListSearchResponseRemote(
    val search: List<SearchReceiveRemote>
)