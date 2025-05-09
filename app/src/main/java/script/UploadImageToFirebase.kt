package script

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.wiseowl.woli.data.local.db.entity.ColorDTO.Companion.toColorDTO
import com.wiseowl.woli.data.local.db.entity.ImageDTO
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.CATEGORY_COLLECTION
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.DATA
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.IMAGES_COLLECTION
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.PAGES_COLLECTION
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.toImages
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import kotlinx.coroutines.tasks.await
import org.koin.java.KoinJavaComponent.inject

class UploadImageToFirebase {
    val detailUseCase: DetailUseCase by inject(DetailUseCase::class.java)

    private suspend fun forEveryDocumentInside(collectionName: String, action: suspend (Map<String, Any>?) -> Unit){
        Firebase.firestore.collection(collectionName).get().await().documents.forEach {
            action(it.data)
        }
    }

    private suspend fun putDocumentIn(collectionName: String, documentId: String, document: Any){
        Firebase.firestore.collection(collectionName).document(documentId).set(document).await()
    }

    suspend fun updateImageSchema(){
        val images = arrayListOf<ImageDTO>()
        forEveryDocumentInside(IMAGES_COLLECTION){

            val image = it?.toImages()
            val bitmap = detailUseCase.getBitmapUseCase(image?.url!!) ?: return@forEveryDocumentInside
            val color = GetColorUseCase().invoke(bitmap).toColorDTO()
            image.color = color
            images.add(image)
        }
        images.forEach {
            putDocumentIn(IMAGES_COLLECTION, it.id.toString(), it)
        }
    }

    suspend fun startUploadingImages(applicationContext: Context) {
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            val images = arrayListOf<ImageDTO>()
            for(line in it.lines()) {
                val keys = line.split(",")
                val url = keys.first()
                val description = keys[1]
                val categories = keys.filterIndexed { index, _ -> index > 1 }
                val bitmap = detailUseCase.getBitmapUseCase(url) ?: throw NullPointerException("Cannot get bitmap")
                val color = GetColorUseCase().invoke(bitmap).toColorDTO()
                val document = Firebase.firestore.collection(IMAGES_COLLECTION).document(url.hashCode().toString()).get().await()
                if(document.exists()) throw Exception("Image already exists")
                images.add(
                    ImageDTO(
                        id = url.hashCode(),
                        url = url,
                        description = description,
                        categories = categories,
                        color = color
                    )
                )
            }
            val categoryScript = FirebaseCategoryScript()
            images.forEach {
                putDocumentIn(IMAGES_COLLECTION, it.id.toString(), it)
                it.categories.forEach { category ->
                    val imageRef = Firebase.firestore.collection(IMAGES_COLLECTION).document(it.id.toString())
                    categoryScript.updateCategory(category, imageRef)
                }
            }
            uploadPage(applicationContext)
        }
    }

    private suspend fun uploadPage(applicationContext: Context){
        val currentPageNo = Firebase.firestore.collection(PAGES_COLLECTION).document(COUNT).get().await().data?.getValue(TOTAL_PAGE) as Long
        val page = currentPageNo+1
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            val imagesRef = arrayListOf<DocumentReference>()
            it.lines().forEach { line ->
                val url = line.split(",").first()
                imagesRef.add(
                    Firebase.firestore.collection(IMAGES_COLLECTION).document(url.hashCode().toString())
                )
            }
            if (imagesRef.isNotEmpty()) {
                Firebase.firestore.collection(PAGES_COLLECTION).document(page.toString()).set(
                    mapOf(DATA to imagesRef)
                )
            }
            Firebase.firestore.collection(PAGES_COLLECTION).document(COUNT).set(mapOf(TOTAL_PAGE to page))
        }
    }

    fun startRemovingImages(applicationContext: Context) {
        applicationContext.assets.open("imagedata.csv").bufferedReader().use {
            it.lines().forEach { line ->
                val url = line.split(",").first()
                Firebase.firestore.collection(IMAGES_COLLECTION).document(url.hashCode().toString()).delete()
            }
        }
    }

    companion object {
        const val COUNT = "count"
        const val TOTAL_PAGE = "totalPages"
    }
}