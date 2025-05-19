package com.wiseowl.woli.data.remote.core

import com.wiseowl.woli.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader(AUTHORIZATION, BuildConfig.PEXELS_API_KEY)
        return chain.proceed(request.build())
    }

    companion object{
        const val AUTHORIZATION = "Authorization"
    }
}