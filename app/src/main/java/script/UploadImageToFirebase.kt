package script

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.data.remote.FirebaseDataService.Companion.IMAGES_DOCUMENT

class UploadImageToFirebase {
    fun startUploadingImages(applicationContext: Context, page: Int) {
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            it.lines().forEach { line ->
                val (url, description, category) = line.split(",")
                Firebase.firestore.collection(IMAGES_DOCUMENT).document(url.hashCode().toString()).set(
                    ImageDTO(
                        id = url.hashCode(),
                        url = url,
                        description = description,
                        category = category
                    )
                )
            }
            uploadPage(applicationContext, page)
        }
    }

    private fun uploadPage(applicationContext: Context, page: Int){
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            val imagesRef = arrayListOf<DocumentReference>()
            it.lines().forEach { line ->
                val url = line.split(",").first()
                imagesRef.add(
                    Firebase.firestore.collection(IMAGES_DOCUMENT).document(url.hashCode().toString())
                )
            }
            if (imagesRef.isNotEmpty()) {
                Firebase.firestore.collection("pages").document(page.toString()).set(
                    mapOf("data" to imagesRef)
                )
            }
        }
    }
}