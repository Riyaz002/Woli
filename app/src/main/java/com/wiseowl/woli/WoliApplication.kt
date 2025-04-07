package com.wiseowl.woli

import android.app.Application
import androidx.room.Room
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.data.local.db.WoliDatabase
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.StringEncryptor
import com.wiseowl.woli.data.remote.FirebaseAPIService
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.usecase.home.PageUseCase
import com.wiseowl.woli.domain.usecase.categories.PageUseCase as CategoriesPageUseCase
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.login.LoginUseCase
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.domain.usecase.detail.GetImageUseCase
import com.wiseowl.woli.domain.usecase.detail.GetBitmapUseCase
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperUseCase
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.domain.usecase.detail.GetImagesForCategoryUseCase
import com.wiseowl.woli.domain.usecase.categories.CategoriesUseCase
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.data.repository.PageRepository
import com.wiseowl.woli.data.repository.ImageRepository
import com.wiseowl.woli.data.repository.CategoryRepository
import com.wiseowl.woli.data.event.EventListener
import com.wiseowl.woli.data.repository.AccountRepository
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
        singleOf(::AccountRepository) bind(com.wiseowl.woli.domain.repository.AccountRepository::class)

        //Event
        singleOf(::EventListener) bind(com.wiseowl.woli.domain.pubsub.EventListener::class)

        //Database
        single { Room.databaseBuilder(this@WoliApplication, WoliDatabase::class.java, WoliDatabase.NAME).build() }

        //Encryption
        single { StringEncryptor() }
        single { EncryptedSharedPreference(this@WoliApplication, get()) }

        //Use Case
        singleOf(::GetImageUseCase)
        singleOf(::GetBitmapUseCase)
        single{ SetWallpaperUseCase(this@WoliApplication) }
        singleOf(::GetImagesForCategoryUseCase)
        singleOf(::PageUseCase)
        singleOf(::CategoriesPageUseCase)
        singleOf(::HomeUseCase)
        singleOf(::DetailUseCase)
        singleOf(::RegistrationUseCase)
        singleOf(::AccountUseCase)
        singleOf(::LoginUseCase)
        singleOf(::GetNavigationItemsUseCase)
        singleOf(::CategoriesUseCase)
        singleOf(::ProfileUseCase)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(appModule)
        }
    }
}