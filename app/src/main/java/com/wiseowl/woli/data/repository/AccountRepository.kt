package com.wiseowl.woli.data.repository

import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository

class AccountRepository(private val remoteApiService: RemoteAPIService): AccountRepository {
    override suspend fun createUser(user: User) {
        remoteApiService.createUser(user)
    }

    override suspend fun deleteUser(userId: String) {
        remoteApiService.deleteUser(userId)
    }

    override suspend fun updateUser(user: User) {
        remoteApiService.updateUser(user)
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return remoteApiService.isEmailRegistered(email)
    }
}