package com.wiseowl.woli.data.remote

import android.content.Context
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.data.local.entity.ColorDTO
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class FirebaseAPIService(val context: Context): RemoteAPIService {
    private val firestore = Firebase.firestore

    override suspend fun getPage(page: Int): List<ImageDTO>? {
        val result = firestore.collection(PAGES_COLLECTION).getDocumentOrNull(page.toString())?.data
        val imagesData = result?.get(DATA) as List<DocumentReference>?
        val images = imagesData?.map { it.get() }?.map { it.await() }
        return images?.map { it.data!!.toImages() }
    }

    override suspend fun getTotalPageCount(): Int {
        val collection = firestore.collection(PAGES_COLLECTION)
        val document = collection.document(COUNT).get().await()
        val totalPageCount = document.data?.getValue(TOTAL_PAGE) as Long
        return totalPageCount.toInt()
    }

    override suspend fun getImage(id: Int): ImageDTO? {
        val result = firestore.collection(IMAGES_COLLECTION).getDocumentOrNull(id.toString())?.data
        val image = (result as Map<String, Any>?)?.toImages()
        return image
    }

    override suspend fun getImages(category: String): List<ImageDTO>? {
        val result = firestore.collection(CATEGORY_COLLECTION).getDocumentOrNull(category)?.data
        val imagesData = result?.get(DATA) as List<DocumentReference>?
        val images = imagesData?.map { it.get() }?.map { it.await() }
        return images?.map { it.data!!.toImages() }
    }

    override suspend fun getImageBitmap(url: String): Bitmap? {
        return withContext(Dispatcher.IO){
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request))
            result.image?.toBitmap()
        }
    }

    override suspend fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<Boolean> {
        val result = Firebase.auth.createUserWithEmailAndPassword(
            email, password
        ).await()

        return if(result.user!=null){
            val user = User(firstName, lastName, result.user!!.uid, email, null)
            firestore.collection(USERS_COLLECTION).document(email).set(user)
            Result.Success(true)
        } else{
            Result.Success(false)
        }
    }

    override suspend fun deleteUser(email: String) {
        firestore.collection(USERS_COLLECTION).document(email).delete()
    }

    override suspend fun updateUser(user: User) {
        firestore.collection(USERS_COLLECTION).document(user.email).set(user)
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return firestore.collection(USERS_COLLECTION).document(email).get().await().exists()
    }

    override suspend fun getUser(email: String): User? {
        return firestore.collection(USERS_COLLECTION).document(email).get().await().data?.toUser()
    }

    companion object{
        const val IMAGES_COLLECTION = "images"
        const val PAGES_COLLECTION = "pages"
        const val CATEGORY_COLLECTION = "category"
        const val USERS_COLLECTION = "users"
        const val COUNT = "count"
        const val TOTAL_PAGE = "totalPages"
        const val DATA = "data"

        fun Map<String, Any>.toImages(): ImageDTO {
            return ImageDTO(
                id = getValue(ImageDTO::id.name).toString().toInt(),
                url = getValue(ImageDTO::url.name).toString(),
                description = getValue(ImageDTO::description.name).toString(),
                categories = getValue(ImageDTO::categories.name) as List<String>,
                color = (getValue(ImageDTO::color.name) as Map<String, Int>?)?.toColorDTO(),
            )
        }

        private fun Map<String, Any>.toColorDTO(): ColorDTO {
            return ColorDTO(
                primary = getValue(ColorDTO::primary.name).toString().toInt(),
                secondary = getValue(ColorDTO::secondary.name).toString().toInt()
            )
        }

        private fun Map<String, Any>.toUser(): User {
            return User(
                firstName = getValue(User::firstName.name).toString(),
                lastName = getValue(User::lastName.name).toString(),
                uid = getValue(User::uid.name).toString(),
                email = getValue(User::email.name).toString(),
                favourites = null
            )
        }

        suspend fun CollectionReference.getDocumentOrNull(documentId: String): DocumentSnapshot?{
            return try {
                document(documentId).get().await()
            } catch (e: Exception){
                null
            }
        }
    }
}