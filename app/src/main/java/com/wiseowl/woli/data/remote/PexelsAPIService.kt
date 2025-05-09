package com.wiseowl.woli.data.remote

import com.wiseowl.woli.BuildConfig
import com.wiseowl.woli.data.service.mediaprovider.model.PageDTO
import com.wiseowl.woli.data.service.mediaprovider.model.PhotoDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsAPIService {

    @GET("curated")
    fun getPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 40,
    ): Call<PageDTO>

    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: Int
    ): Call<PhotoDTO>

    @GET("search")
    fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 40,
    ): Call<PageDTO>

    companion object{
        fun getInstance(): PexelsAPIService {
            return Retrofit
                .Builder()
                .baseUrl(BuildConfig.PEXELS_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(PexelsAPIService::class.java)
        }
    }
}