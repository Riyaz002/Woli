package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.User

class TestAccountRepository: AccountRepository {
    private val users = mutableListOf<User>()
    override suspend fun createUser(user: User) {
        users.add(user)
    }

    override suspend fun deleteUser(userId: String) {
        users.removeIf { it.email == userId }
    }

    override suspend fun updateUser(user: User) {
        users[users.indexOfFirst { it.email == user.email }] = user
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return users.any { it.email == email }
    }

    override suspend fun getUser(email: String): User {
        return users.first{ it.email == email }
    }
}