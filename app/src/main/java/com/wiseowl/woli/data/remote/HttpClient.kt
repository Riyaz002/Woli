package com.wiseowl.woli.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.wiseowl.woli.data.remote.core.HeaderInterceptor
import okhttp3.OkHttpClient

class HttpClient {
    fun get(applicationContext: Context): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(applicationContext))
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}