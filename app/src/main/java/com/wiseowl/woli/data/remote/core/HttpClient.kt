package com.wiseowl.woli.data.remote.core

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

const val CACHE_SIZE = 50L * 1024L * 1024L //50MB

class HttpClient {
    fun get(applicationContext: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(applicationContext))
            .addInterceptor(HeaderInterceptor())
            .cache(
                Cache(
                    directory = File(applicationContext.cacheDir, "http_cache"),
                    maxSize = CACHE_SIZE // 50 MiB
                )
            )
            .build()
    }
}