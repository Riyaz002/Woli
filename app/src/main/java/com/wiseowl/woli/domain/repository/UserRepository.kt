package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun deleteUser(userId: String)
    suspend fun isUserValid(userId: String): Boolean?
    suspend fun updateUser(user: User)
}