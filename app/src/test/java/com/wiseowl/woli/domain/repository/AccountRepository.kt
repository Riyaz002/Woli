package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.AccountState
import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class TestAccountRepository: AccountRepository {
    private val users = mutableListOf<User>()
    private var currentUser: User? = null

    private val accountState = MutableStateFlow(
        AccountState(
            false,
            null
        )
    )

    override fun observerAccountState(): StateFlow<AccountState> = accountState

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
            accountState.update {
                AccountState(
                    true,
                    currentUser
                )
            }
            Result.Success(currentUser!!)
        } else Result.Error(Error("User not found"))
    }

    override suspend fun deleteUser() {
        users.removeIf { it.email == currentUser?.email }
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return users.any { it.email == email }
    }

    override suspend fun getUserInfo(): User? {
        return currentUser
    }

    override suspend fun signOut(): Result<Boolean> {
        accountState.update {
            AccountState(
                false,
                null
            )
        }
        return Result.Success(true)
    }
}
