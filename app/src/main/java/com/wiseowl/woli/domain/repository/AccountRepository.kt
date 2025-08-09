package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.AccountState
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    fun getAccountState(): StateFlow<AccountState>
    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun deleteUser()
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getUserInfo(): User?
    suspend fun addToFavourites(mediaId: Long)
    suspend fun signOut(): Result<Boolean>
}