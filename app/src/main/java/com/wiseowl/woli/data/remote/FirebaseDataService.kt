package com.wiseowl.woli.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.domain.RemoteDataService
import kotlinx.coroutines.tasks.await

@Suppress("UNCHECKED_CAST")
class FirebaseDataService: RemoteDataService {
    private val firestore = Firebase.firestore

    override suspend fun getPage(page: Int): List<ImageDTO>? {
        val result = firestore.collection(PAGES_DOCUMENT).getDocumentOrNull(page.toString())?.data
        val imagesData = result?.get(DATA) as List<DocumentReference>?
        val images = imagesData?.map { it.get() }?.map { it.await() }
        return images?.map { it.data!!.toImages() }
    }

    companion object{
        const val IMAGES_DOCUMENT = "images"
        const val PAGES_DOCUMENT = "pages"
        const val DATA = "data"

        private fun Map<String, Any>.toImages(): ImageDTO {
            return ImageDTO(
                id = getValue(ImageDTO::id.name).toString().toInt(),
                url = getValue(ImageDTO::url.name).toString(),
                description = getValue(ImageDTO::description.name).toString(),
                category = getValue(ImageDTO::category.name).toString()
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