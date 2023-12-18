package com.example.wallmaster.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface ImageInfoApi {
    @GET("/groups/fetch/{imageType}")
    suspend fun getTags(@Path("imageType") imageType: String): ImageInfo
}