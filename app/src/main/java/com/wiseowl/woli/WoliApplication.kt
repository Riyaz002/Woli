package com.wiseowl.woli

import android.app.Application
import com.wiseowl.woli.di.appModule
import org.koin.core.context.startKoin

class WoliApplication: Application() {
    private val appModule = appModule(this)

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(appModule)
        }
    }
}