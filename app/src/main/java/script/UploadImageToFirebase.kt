package script

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.wiseowl.woli.data.local.entity.ImageDTO
import com.wiseowl.woli.data.remote.FirebaseDataService.Companion.IMAGES_DOCUMENT
import com.wiseowl.woli.data.remote.FirebaseDataService.Companion.PAGES_DOCUMENT
import kotlinx.coroutines.tasks.await

class UploadImageToFirebase {
    suspend fun startUploadingImages(applicationContext: Context) {
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
            uploadPage(applicationContext)
        }
    }

    private suspend fun uploadPage(applicationContext: Context){
        val currentPageNo = Firebase.firestore.collection(PAGES_DOCUMENT).document(COUNT).get().await().data?.getValue(TOTAL_PAGE) as Long
        val page = currentPageNo+1
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            val imagesRef = arrayListOf<DocumentReference>()
            it.lines().forEach { line ->
                val url = line.split(",").first()
                imagesRef.add(
                    Firebase.firestore.collection(IMAGES_DOCUMENT).document(url.hashCode().toString())
                )
            }
            if (imagesRef.isNotEmpty()) {
                Firebase.firestore.collection(PAGES_DOCUMENT).document(page.toString()).set(
                    mapOf("data" to imagesRef)
                )
            }
            Firebase.firestore.collection(PAGES_DOCUMENT).document(COUNT).set(mapOf(TOTAL_PAGE to page))
        }
    }

    companion object{
        const val COUNT = "count"
        const val TOTAL_PAGE = "totalPages"
    }
}