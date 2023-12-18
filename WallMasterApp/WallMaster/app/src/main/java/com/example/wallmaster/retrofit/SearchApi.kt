package com.example.wallmaster.retrofit

import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {
    @POST("search")
    suspend fun search(@Body searchRequest: SearchRequestRemote): ListSearchResponseRemote
}