package com.wiseowl.woli.di

import android.content.Context
import androidx.room.Room
import com.wiseowl.woli.data.event.EventListener
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.StringEncryptor
import com.wiseowl.woli.data.remote.core.HttpClient
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService
import com.wiseowl.woli.data.remote.media.PexelsAPIService
import com.wiseowl.woli.data.repository.AccountRepository
import com.wiseowl.woli.data.repository.ImageRepository
import com.wiseowl.woli.data.repository.media.MediaRepositoryImpl
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.service.DownloadManagerService
import com.wiseowl.woli.domain.service.DownloadManagerServiceImpl
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.media.GetCollectionsPageUseCase
import com.wiseowl.woli.domain.usecase.common.media.GetCollectionPageUseCase
import com.wiseowl.woli.domain.usecase.common.media.GetPhotoPageUseCase
import com.wiseowl.woli.domain.usecase.common.media.GetPhotoUseCase
import com.wiseowl.woli.domain.usecase.common.media.GetSearchUseCase
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.usecase.detail.GetBitmapUseCase
import com.wiseowl.woli.domain.usecase.detail.GetComplementaryColorUseCase
import com.wiseowl.woli.domain.usecase.detail.SaveFileUseCase
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperUseCase
import com.wiseowl.woli.domain.usecase.login.LoginUseCase
import com.wiseowl.woli.domain.usecase.main.GetNavigationItemsUseCase
import com.wiseowl.woli.domain.usecase.privacypolicy.GetPrivacyPolicyUseCase
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.ui.notification.NotificationHandler
import com.wiseowl.woli.ui.notification.handler.WoliNotificationHandler
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun appModule(application: Context) = module {
    //RemoteApi
    single{ FirebaseAPIService(application) } bind(RemoteAPIService::class)
    single{ HttpClient().get(application) } bind(OkHttpClient::class)
    single{ PexelsAPIService.getInstance(get()) } bind(PexelsAPIService::class)

    //Repository
    singleOf(::ImageRepository) bind(com.wiseowl.woli.domain.repository.ImageRepository::class)
    singleOf(::AccountRepository) bind(com.wiseowl.woli.domain.repository.AccountRepository::class)
    singleOf(::MediaRepositoryImpl) bind(com.wiseowl.woli.domain.repository.media.MediaRepository::class)

    //Event
    singleOf(::EventListener) bind(com.wiseowl.woli.domain.event.EventListener::class)

    //Encryption
    single { StringEncryptor() }
    single { EncryptedSharedPreference(application, get()) }

    //Services
    single<DownloadManagerService> { DownloadManagerServiceImpl(application) }

    //Notification
    single { WoliNotificationHandler(application) } bind(NotificationHandler::class)

    //Use Case
    singleOf(::GetBitmapUseCase)
    single{ SetWallpaperUseCase(application) }
    singleOf(::DetailUseCase)
    singleOf(::RegistrationUseCase)
    singleOf(::AccountUseCase)
    singleOf(::LoginUseCase)
    singleOf(::GetNavigationItemsUseCase)
    singleOf(::ProfileUseCase)
    singleOf(::GetPrivacyPolicyUseCase)
    singleOf(::GetPhotoPageUseCase)
    singleOf(::GetSearchUseCase)
    singleOf(::GetComplementaryColorUseCase)
    singleOf(::GetPhotoUseCase)
    singleOf(::GetCollectionsPageUseCase)
    singleOf(::GetCollectionPageUseCase)
    singleOf(::MediaUseCase)
    singleOf(::SaveFileUseCase)
}