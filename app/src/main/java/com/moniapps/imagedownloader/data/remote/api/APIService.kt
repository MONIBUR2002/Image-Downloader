package com.moniapps.imagedownloader.data.remote.api

import com.moniapps.imagedownloader.data.remote.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface APIService {

    companion object {
        val BASE_URL = "https://api.pexels.com/v1/"
    }

    @GET("search")
   suspend fun searchPhotos(
        @Query("query") searchQuery: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Response<Photos>

}