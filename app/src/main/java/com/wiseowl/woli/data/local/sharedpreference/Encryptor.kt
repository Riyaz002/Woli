package com.wiseowl.woli.data.local.sharedpreference

interface Encryptor<T> {
    fun encrypt(value: T): T
    fun decrypt(value: T): T
}