package com.wiseowl.woli.data.repository

import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository

class AccountRepository(private val remoteApiService: RemoteAPIService): AccountRepository {


    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(email: String) {
        remoteApiService.deleteUser(email)
    }

    override suspend fun updateUser(user: User) {
        remoteApiService.updateUser(user)
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return remoteApiService.isEmailRegistered(email)
    }

    override suspend fun getUser(email: String): User? {
        return remoteApiService.getUser(email)
    }
}