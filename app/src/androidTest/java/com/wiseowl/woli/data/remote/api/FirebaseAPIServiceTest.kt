package com.wiseowl.woli.data.remote.api

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.wiseowl.woli.data.remote.FirebaseAPIService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class FirebaseAPIServiceTest{

    @Test
    fun getPageReturnAValidPage() = runBlocking{
        Firebase.initialize(getApplicationContext())
        val result = FirebaseAPIService().getPage(1)
        Assert.assertNotNull(result?.isNotEmpty())
    }
}