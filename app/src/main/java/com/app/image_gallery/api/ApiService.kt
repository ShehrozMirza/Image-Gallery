package com.app.image_gallery.api

import com.app.image_gallery.models.PixabayApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun searchPhotos(
            @Query("key") key: String?,
            @Query("q") query: String?,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
    ): PixabayApiResponse
}