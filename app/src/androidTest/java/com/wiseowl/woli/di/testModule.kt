package com.wiseowl.woli.di

import com.wiseowl.woli.domain.service.DownloadManagerService
import com.wiseowl.woli.domain.usecase.detail.SaveFileUseCase
import com.wiseowl.woli.service.FakeDownloadManagerService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun testModule() = module{
    single{ SaveFileUseCase(FakeDownloadManagerService()) }
}