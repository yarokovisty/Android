package com.example.wallmaster.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface GroupImgApi {
    @GET("/groups/fetch/{imageType}")
    suspend fun getImages(@Path("imageType") imageType: String): GroupImg
}