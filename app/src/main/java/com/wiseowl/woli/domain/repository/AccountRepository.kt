package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result

interface AccountRepository {
    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String): Result<Boolean>
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun deleteUser(email: String)
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getUser(email: String): User?
}