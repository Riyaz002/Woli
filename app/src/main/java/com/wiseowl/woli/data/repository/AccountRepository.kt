package com.wiseowl.woli.data.repository

import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference.Companion.USER
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.AccountState
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AccountRepository(
    private val remoteApiService: RemoteAPIService,
    private val sharedPreference: EncryptedSharedPreference
) : AccountRepository {

    val scope = CoroutineScope(Dispatcher.IO)

    private val accountState = MutableStateFlow(
        AccountState(
            isLoggedIn = getSavedUser()!=null,
            currentUser = getSavedUser()
        )
    )

    init {
        scope.launch {
            val currentUser = scope.async { getUserInfo() }.await()
            accountState.update {
                AccountState(
                    isLoggedIn = currentUser != null,
                    currentUser = currentUser
                )
            }
        }
    }

    override fun getAccountState(): StateFlow<AccountState> = accountState


    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<User> {
        val result = remoteApiService.createUser(email, password, firstName, lastName)
        if (result is Result.Success) saveUser(result.data)
        return result
    }

    override suspend fun login(email: String, password: String): Result<User> {
        val result = remoteApiService.login(email, password)
        accountState.update {
            when (result) {
                is Result.Success<User> -> AccountState(
                    isLoggedIn = true,
                    currentUser = result.data
                )
                else -> AccountState(
                    isLoggedIn = false,
                    currentUser = null
                )
            }

        }

        return result
    }

    override suspend fun deleteUser() {
        remoteApiService.deleteUser()
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return remoteApiService.isEmailRegistered(email)
    }

    override suspend fun getUserInfo(): User? {
        var user = getSavedUser()
        if (user != null) return user
        user = remoteApiService.getUserInfo()
        if (user != null) saveUser(user)
        return user
    }

    override suspend fun signOut(): Result<Boolean> {
        remoteApiService.signOut()
        sharedPreference.clear()
        accountState.update {
            AccountState(
                isLoggedIn = false,
                currentUser = null
            )
        }
        return Result.Success(true)
    }

    private fun saveUser(user: User) {
        sharedPreference.put(USER, Json.encodeToString(user))
    }

    private fun getSavedUser(): User? {
        val userString = sharedPreference.get(USER)
        return if (userString != null) {
            Json.decodeFromString<User>(userString)
        } else null
    }
}