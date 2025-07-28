package com.wiseowl.woli.data.local.credential

import androidx.test.platform.app.InstrumentationRegistry
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.StringEncryptor
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class EncryptedSharedPreferenceTest{
    private lateinit var sharedPreference: EncryptedSharedPreference

    @Before
    fun setUp(){
        sharedPreference = EncryptedSharedPreference(InstrumentationRegistry.getInstrumentation().context, StringEncryptor())
    }

    @Test
    fun sharedPreference_save_and_retrieve_data_successfully(){
        val key = "testKey"
        val value = "() {} , = Any value that can be saved"
        sharedPreference.put(key, value)

        val retrievedValue = sharedPreference.get(key)
        assertEquals(value, retrievedValue)
    }
}