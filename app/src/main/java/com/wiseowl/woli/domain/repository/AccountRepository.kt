package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.User

interface AccountRepository {
    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String)
    suspend fun deleteUser(email: String)
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getUser(email: String): User?
}