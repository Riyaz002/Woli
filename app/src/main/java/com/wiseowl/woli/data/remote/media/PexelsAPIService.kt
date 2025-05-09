package com.wiseowl.woli.data.remote.media

import com.wiseowl.woli.BuildConfig
import com.wiseowl.woli.data.remote.media.model.PageDTO
import com.wiseowl.woli.data.remote.media.model.PhotoDTO
import okhttp3.OkHttpClient
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
        fun getInstance(okHttpClient: OkHttpClient): PexelsAPIService{
            return Retrofit
                .Builder()
                .baseUrl(BuildConfig.PEXELS_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(PexelsAPIService::class.java)
        }
    }
}