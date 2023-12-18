package com.example.wallmaster.retrofit

import retrofit2.http.GET

interface TitleApi {
    @GET("groups/fetch/titles")
    suspend fun getTitle(): Title
}