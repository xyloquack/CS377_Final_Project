package com.jsb536.cs377_final_project.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: UnsplashApiService by lazy {
        Retrofit.Builder()
            .baseUrl(UnsplashApiService.Companion.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApiService::class.java)
    }
}