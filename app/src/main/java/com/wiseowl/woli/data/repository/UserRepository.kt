package com.wiseowl.woli.data.repository

import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.UserRepository

class UserRepository(private val remoteApiService: RemoteAPIService): UserRepository {
    override suspend fun createUser(user: User) {
        remoteApiService.createUser(user)
    }

    override suspend fun deleteUser(userId: String) {
        remoteApiService.deleteUser(userId)
    }

    override suspend fun isUserValid(userId: String): Boolean? {
        return remoteApiService.isUserValid(userId)
    }

    override suspend fun updateUser(user: User) {
        remoteApiService.updateUser(user)
    }
}