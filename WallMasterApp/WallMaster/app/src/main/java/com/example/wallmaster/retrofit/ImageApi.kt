package com.example.wallmaster.retrofit

import retrofit2.http.GET

interface ImageApi {
    @GET("/groups/fetch/groupsImg")
    suspend fun getImage(): Image
}