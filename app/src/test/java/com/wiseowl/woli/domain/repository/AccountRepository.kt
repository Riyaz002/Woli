package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result
import java.util.UUID

class TestAccountRepository: AccountRepository {
    private val users = mutableListOf<User>()
    override fun isLoggedIn(): Boolean = currentUser!=null
    private var currentUser: User? = null

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<User> {
        if(users.any { it.email == email }){
            return Result.Error(Error("Email already registered"))
        }
        currentUser = User(firstName, lastName, UUID.randomUUID().toString(), email, null)
        users.add(currentUser!!)
        return Result.Success(currentUser!!)
    }

    override suspend fun login(email: String, password: String): Result<User> {
        currentUser = users.firstOrNull { it.email == email }
        return if(currentUser!=null){
            Result.Success(currentUser!!)
        } else Result.Error(Error("User not found"))
    }

    override suspend fun deleteUser() {
        users.removeIf { it.email == currentUser?.email }
    }

    override suspend fun updateUser(user: User) {
        users[users.indexOfFirst { it.email == user.email }] = user
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return users.any { it.email == email }
    }

    override suspend fun getUserInfo(): User? {
        return currentUser
    }
}
