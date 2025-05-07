package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result

interface AccountRepository {
    fun isLoggedIn(): Boolean
    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun deleteUser()
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getUserInfo(): User?
}