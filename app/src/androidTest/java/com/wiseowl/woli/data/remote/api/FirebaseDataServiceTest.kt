package com.wiseowl.woli.data.remote.api

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.firebase.Firebase
import com.google.firebase.initialize
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class FirebaseDataServiceTest{

    @Test
    fun getPageReturnAValidPage() = runBlocking{
        Firebase.initialize(getApplicationContext())
        val result = FirebaseDataService().getPage(1)
        Assert.assertNotNull(result)
    }
}