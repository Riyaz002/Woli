package com.wiseowl.woli.domain

import coil3.Bitmap
import com.wiseowl.woli.domain.model.Policy
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result

interface RemoteAPIService {
    suspend fun getImageBitmap(url: String): Bitmap?
    suspend fun getPrivacyPolicyPage(): List<Policy>?

    //Account
    fun isLoggedIn(): Boolean
    suspend fun createUser(email: String, password: String, firstName: String, lastName: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun deleteUser()
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getUserInfo(): User?
    suspend fun addToFavourites(mediaId: Long)
    suspend fun getFavourites(): List<Long>
    suspend fun signOut()
}