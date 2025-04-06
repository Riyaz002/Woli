package com.wiseowl.woli.domain

import coil3.Bitmap
import com.wiseowl.woli.data.local.db.entity.CategoryDTO
import com.wiseowl.woli.data.local.db.entity.ImageDTO
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result

interface RemoteAPIService {
    suspend fun getPage(page: Int): List<ImageDTO>?
    suspend fun getTotalPageCount(): Int
    suspend fun getImage(id: Int): ImageDTO?
    suspend fun getImages(category: String): List<ImageDTO>?
    suspend fun getImageBitmap(url: String): Bitmap?
    suspend fun getCategoryPage(page: Int): List<CategoryDTO>?

    //Account
    suspend fun createUser(email: String, password: String, firstName: String, lastName: String): Result<Boolean>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun deleteUser(email: String)
    suspend fun updateUser(user: User)
    suspend fun isEmailRegistered(email: String): Boolean
    suspend fun getUser(email: String): User?
}