package com.wiseowl.woli.domain

import coil3.Bitmap
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.domain.model.User

interface RemoteAPIService {
    suspend fun getPage(page: Int): List<ImageDTO>?
    suspend fun getTotalPageCount(): Int
    suspend fun getImage(id: Int): ImageDTO?
    suspend fun getImages(category: String): List<ImageDTO>?
    suspend fun getImageBitmap(url: String): Bitmap?

    //Account
    suspend fun createUser(user: User)
    suspend fun deleteUser(userId: String)
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
}