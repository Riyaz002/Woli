package com.wiseowl.woli.data.repository

import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference
import com.wiseowl.woli.data.local.sharedpreference.EncryptedSharedPreference.Companion.USER
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.AccountState
import com.wiseowl.woli.domain.model.GuestUser
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AccountRepository(
    private val remoteApiService: RemoteAPIService,
    private val sharedPreference: EncryptedSharedPreference
) : AccountRepository {

    private val scope = CoroutineScope(Dispatcher.IO)
    override val accountState = MutableStateFlow(AccountState(false, GuestUser)).apply {
        scope.launch {
            val currentUser = getUserInfo()
            val isLoggedIn = currentUser.uid != GuestUser.uid
            if(!isLoggedIn) GuestUser = currentUser
            emit(
                AccountState(
                    isLoggedIn,
                    currentUser
                )
            )
        }
    }

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<User> {
        val result = remoteApiService.createUser(email, password, firstName, lastName)
        if (result is Result.Success) updateUser(result.data)
        return result
    }

    override suspend fun login(email: String, password: String): Result<User> {
        val result = remoteApiService.login(email, password)
        if(result is Result.Success) updateUser(result.data)
        return result
    }

    override suspend fun deleteUser() {
        remoteApiService.deleteUser()
        updateUser(null)
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return remoteApiService.isEmailRegistered(email)
    }

    override suspend fun getUserInfo(): User {
        var user = getSavedUser()
        if (user != null) return user
        user = remoteApiService.getUserInfo() ?: GuestUser
        updateUser(user)
        return user
    }

    override suspend fun addToFavourites(mediaId: Long) {
        with(accountState.value.currentUser){
            updateUser(this?.copy(favourites = favourites.orEmpty()+mediaId))
        }
        remoteApiService.addToFavourites(mediaId)
    }

    override suspend fun getFavourites(): List<Long> {
        return accountState.value.currentUser?.favourites.orEmpty()

    }

    override suspend fun removeFromFavourites(mediaId: Long) {
        with(accountState.value.currentUser){
            updateUser(this?.copy(favourites = favourites.orEmpty()-mediaId))
        }
        remoteApiService.removeFromFavourites(mediaId)
    }

    override suspend fun signOut(): Result<Boolean> {
        remoteApiService.signOut()
        updateUser(null)
        return Result.Success(true)
    }

    private fun updateUser(user: User?) {
        val updatedUser = user ?: GuestUser
        val isGuestUser = GuestUser.uid == updatedUser.uid
        if(isGuestUser) GuestUser = updatedUser
        accountState.update {
            AccountState(
                !isGuestUser,
                user
            )
        }
        sharedPreference.put(USER, Json.encodeToString(user))
    }

    private fun getSavedUser(): User? {
        val userString = sharedPreference.get(USER)
        return if (userString != null) {
            Json.decodeFromString<User>(userString)
        } else null
    }
}