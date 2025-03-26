package com.wiseowl.woli.data.repository

import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result

class AccountRepository(private val remoteApiService: RemoteAPIService): AccountRepository {


    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<Boolean> {
        return remoteApiService.createUser(email, password, firstName, lastName)
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