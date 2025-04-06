package com.wiseowl.woli.data.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class EncryptedSharedPreference(context: Context, private val encryptor: StringEncryptor) {
    init {
        synchronized(lock) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            }
        }
    }

    fun put(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, encryptor.encrypt(value))?.apply()
    }

    fun get(key: String): String? {
        val encryptedValue = sharedPreferences?.getString(key, null)
        return if (encryptedValue != null) encryptor.decrypt(encryptedValue) else null
    }


    companion object {
        private val lock = Any()
        @Volatile
        private var sharedPreferences: SharedPreferences? = null
        private const val NAME = "WOLI_SHARED_PREF"

        //keys
        const val USER = "user"
    }
}