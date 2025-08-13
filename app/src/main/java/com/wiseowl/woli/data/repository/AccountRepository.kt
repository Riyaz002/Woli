package com.wiseowl.woli.data.repository

import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference.Companion.USER
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.AccountState
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AccountRepository(
    private val remoteApiService: RemoteAPIService,
    private val sharedPreference: EncryptedSharedPreference
) : AccountRepository {

    private val scope = CoroutineScope(Dispatcher.IO)

    override var accountState: StateFlow<AccountState> = remoteApiService.userState.map {
        AccountState(
            isLoggedIn = it != null,
            currentUser = it
        )
    }.stateIn(
        scope,
        started = SharingStarted.Eagerly,
        initialValue = AccountState(
            isLoggedIn = getSavedUser() != null,
            currentUser = getSavedUser()
        )
    )

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

    override suspend fun addToFavourites(mediaId: Long) {
        if (accountState.value.isLoggedIn) {
            remoteApiService.addToFavourites(mediaId)
        }
    }

    override suspend fun getFavourites(): List<Long> {
        return if (accountState.value.isLoggedIn) {
            remoteApiService.getFavourites()
        } else emptyList()
    }

    override suspend fun removeFromFavourites(mediaId: Long) {
        if (accountState.value.isLoggedIn) {
            remoteApiService.removeFromFavourites(mediaId)
        }
    }

    override suspend fun signOut(): Result<Boolean> {
        remoteApiService.signOut()
        sharedPreference.clear()
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