package com.wiseowl.woli

import android.app.Application
import androidx.room.Room
import com.wiseowl.woli.data.WoliRepositoryImpl
import com.wiseowl.woli.domain.RemoteDataService
import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.data.local.WoliDatabase
import com.wiseowl.woli.data.remote.FirebaseDataService
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.usecase.home.GetPageUseCase
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.domain.usecase.detail.GetImageUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class WoliApplication: Application() {
    private val appModule = module {
        singleOf(::FirebaseDataService) bind(RemoteDataService::class)
        singleOf(::WoliRepositoryImpl) bind(WoliRepository::class)
        single { Room.databaseBuilder(this@WoliApplication, WoliDatabase::class.java, WoliDatabase.NAME).build() }

        //Use Case
        singleOf(::GetImageUseCase)
        singleOf(::GetPageUseCase)
        singleOf(::HomeUseCase)
        singleOf(::DetailUseCase)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(appModule)
        }
    }
}