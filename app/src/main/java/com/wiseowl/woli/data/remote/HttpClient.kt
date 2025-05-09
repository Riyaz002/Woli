package com.wiseowl.woli.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient

class HttpClient {
    fun get(applicationContext: Context): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(applicationContext))
            .build()
    }
}