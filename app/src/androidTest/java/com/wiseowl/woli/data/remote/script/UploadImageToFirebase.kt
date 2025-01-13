package com.wiseowl.woli.data.remote.script

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.data.remote.api.FirebaseDataService.Companion.IMAGES_DOCUMENT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL

class UploadImageToFirebase {
    @Test
    fun upload() = runBlocking{
        startUploading(InstrumentationRegistry.getInstrumentation().targetContext, "1")
    }

    private fun startUploading(applicationContext: Context, documentName: String) {
        GlobalScope.launch {
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
                if(images.isNotEmpty()){
                    Firebase.firestore.collection(IMAGES_DOCUMENT).document(documentName).set(
                        mapOf("data" to images)
                    )
                }
            }
        }
    }
}