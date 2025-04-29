package com.jsb536.cs377_final_project.api

import com.jsb536.cs377_final_project.BuildConfig
import com.jsb536.cs377_final_project.UnsplashSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashApiService {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query ("per_page") perPage: Int,
    @Header("Authorization") authorization: String = "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}"
    ): UnsplashSearchResponse
}