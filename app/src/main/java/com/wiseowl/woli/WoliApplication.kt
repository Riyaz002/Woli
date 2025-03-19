package com.wiseowl.woli

import android.app.Application
import androidx.room.Room
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.data.local.WoliDatabase
import com.wiseowl.woli.data.remote.FirebaseAPIService
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.usecase.home.PageUseCase
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.domain.usecase.detail.GetImageUseCase
import com.wiseowl.woli.domain.usecase.detail.GetBitmapUseCase
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperUseCase
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.domain.usecase.detail.GetImagesForCategoryUseCase
import com.wiseowl.woli.data.repository.PageRepository
import com.wiseowl.woli.data.repository.ImageRepository
import com.wiseowl.woli.data.repository.CategoryRepository
import com.wiseowl.woli.data.repository.UserRepository
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class WoliApplication: Application() {
    private val appModule = module {
        //RemoteApi
        single{ FirebaseAPIService(this@WoliApplication) } bind(RemoteAPIService::class)
        
        //Repository
        singleOf(::PageRepository) bind(com.wiseowl.woli.domain.repository.PageRepository::class)
        singleOf(::ImageRepository) bind(com.wiseowl.woli.domain.repository.ImageRepository::class)
        singleOf(::CategoryRepository) bind(com.wiseowl.woli.domain.repository.CategoryRepository::class)
        singleOf(::UserRepository) bind(com.wiseowl.woli.domain.repository.UserRepository::class)
        
        //Database
        single { Room.databaseBuilder(this@WoliApplication, WoliDatabase::class.java, WoliDatabase.NAME).build() }

        //Use Case
        singleOf(::GetImageUseCase)
        singleOf(::GetBitmapUseCase)
        single{ SetWallpaperUseCase(this@WoliApplication) }
        singleOf(::GetImagesForCategoryUseCase)
        singleOf(::PageUseCase)
        singleOf(::HomeUseCase)
        singleOf(::DetailUseCase)
        singleOf(::GetNavigationItemsUseCase)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(appModule)
        }
    }
}