package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.User

interface AccountRepository {
    suspend fun createUser(user: User)
    suspend fun deleteUser(userId: String)
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
}