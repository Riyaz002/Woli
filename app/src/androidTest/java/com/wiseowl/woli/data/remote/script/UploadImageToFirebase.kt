package com.wiseowl.woli.data.remote.script

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.data.remote.api.FirebaseDataService.Companion.IMAGES_DOCUMENT
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UploadImageToFirebase {
    @Test
    fun upload() = runBlocking {
        startUploading(getApplicationContext(), "1")
    }

    private fun startUploading(applicationContext: Context, documentName: String) {
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            val images = arrayListOf<ImageDTO>()
            it.lines().forEach { line ->
                val (url, description, category) = line.split(",")
                images.add(
                    ImageDTO(
                        id = url.hashCode(),
                        url = url,
                        description = description,
                        category = category
                    )
                )
            }
            if (images.isNotEmpty()) {
                Log.e("TEST", images.toString())
                Firebase.firestore.collection(IMAGES_DOCUMENT).document(documentName).set(
                    mapOf("data" to images)
                )
            }
        }
    }
}