package com.wiseowl.woli.data.remote.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.data.remote.RemoteDataService
import kotlinx.coroutines.tasks.await

class FirebaseDataService: RemoteDataService {
    private val firestore = Firebase.firestore

    override suspend fun getPage(page: Int): List<ImageDTO>? {
        val result = firestore.collection(IMAGES_DOCUMENT).document(page.toString()).get().await().data
        return result?.get("data")?.toImages()
    }
    companion object{
        const val IMAGES_DOCUMENT = "images"

        private fun Any.toImages(): List<ImageDTO>{
            return listOf()
        }
    }
}