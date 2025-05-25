package com.wiseowl.woli.usecase.detail

import android.os.Environment
import androidx.core.net.toUri
import com.wiseowl.woli.di.testModule
import com.wiseowl.woli.domain.usecase.detail.SaveFileUseCase
import com.wiseowl.woli.rule.KoinModuleRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.java.KoinJavaComponent.get

class SaveFileUseCaseTest {

    @get:Rule
    val koinModuleRule: KoinModuleRule = KoinModuleRule(testModule())

    lateinit var saveFileUseCase: SaveFileUseCase

    @Before
    fun setUp(){
        saveFileUseCase =  get(SaveFileUseCase::class.java)
    }

    @Test
    fun saveFileUseCase_return_Long() {
        val id = saveFileUseCase.invoke(
            Environment.getDownloadCacheDirectory().toUri(),
            Environment.getDownloadCacheDirectory().toUri(),
            "Some title",
            "description"
        )
        Assert.assertNotNull(id)
    }
}