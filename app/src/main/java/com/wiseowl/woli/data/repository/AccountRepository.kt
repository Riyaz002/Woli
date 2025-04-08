package com.wiseowl.woli.data.repository

import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference.Companion.USER
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result
import kotlinx.serialization.json.Json

class AccountRepository(private val remoteApiService: RemoteAPIService, private val sharedPreference: EncryptedSharedPreference): AccountRepository {
    override fun isLoggedIn(): Boolean = remoteApiService.isLoggedIn()


    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<Boolean> {
        return remoteApiService.createUser(email, password, firstName, lastName)
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return remoteApiService.login(email, password)
    }

    override suspend fun deleteUser() {
        remoteApiService.deleteUser()
    }

    override suspend fun updateUser(user: User) {
        remoteApiService.updateUser(user)
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return remoteApiService.isEmailRegistered(email)
    }

    override suspend fun getUserInfo(): User? {
        var user = getSavedUser()
        if(user!=null) return user
        user = remoteApiService.getUserInfo()
        if(user!=null) saveUser(user)
        return user
    }

    private fun saveUser(user: User) {
        sharedPreference.put(USER, Json.encodeToString(user))
    }

    private fun getSavedUser(): User?{
        val userString = sharedPreference.get(USER)
        return if(userString!=null){
            Json.decodeFromString<User>(userString)
        } else null
    }
}