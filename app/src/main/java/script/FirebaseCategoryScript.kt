package script

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.wiseowl.woli.data.local.db.entity.ImageDTO
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.CATEGORY_COLLECTION
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.DATA
import com.wiseowl.woli.data.remote.firebase.FirebaseAPIService.Companion.IMAGES_COLLECTION
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirebaseCategoryScript {

    private suspend fun forEveryDocumentInside(collectionName: String, action: suspend (id: String, Map<String, Any>?) -> Unit){
        Firebase.firestore.collection(collectionName).get().await().documents.forEach {
            action(it.id, it.data)
        }
    }

    private suspend fun getDocuments(collectionName: String) = Firebase.firestore.collection(collectionName).get().await()

    private suspend fun putDocumentIn(collectionName: String, documentId: String, document: Any){
        Firebase.firestore.collection(collectionName).document(documentId).set(document).await()
    }

    fun updateCategories(){
        GlobalScope.launch {
            getDocuments("category").documents.chunked(10).forEachIndexed { index, documentSnapshots ->
                val documents = documentSnapshots.map { it.reference }
                putDocumentIn("categories", index.plus(1).toString(), mapOf(DATA to documents))
            }
        }
    }

    fun setCategoriesCover(){
        GlobalScope.launch {
            forEveryDocumentInside(CATEGORY_COLLECTION){ id, data ->
                val images = data?.get(DATA) as List<DocumentReference>?
                val newData = data as MutableMap<String, Any>
                newData["cover"] = images?.get(0) as Any
                newData["name"] = id
                putDocumentIn(
                    CATEGORY_COLLECTION,
                    id,
                    newData
                )
            }
        }
    }

    suspend fun updateAllCategory(){
        forEveryDocumentInside(IMAGES_COLLECTION){ id, it ->
            val categories = it?.getValue(ImageDTO::categories.name) as List<String>
            val id = it.getValue(ImageDTO::id.name).toString().toInt()
            categories.forEach { category ->
                val imageRef = Firebase.firestore.collection(IMAGES_COLLECTION).document(id.toString())
                val categoryData = Firebase.firestore.collection(CATEGORY_COLLECTION).document(category).get().await().data?.get(DATA) as List<DocumentReference>?
                var categoryNewData = categoryData?.toMutableList() ?: mutableListOf()
                categoryNewData.add(imageRef)
                categoryNewData = categoryNewData.distinctBy { it.id }.toMutableList()
                Firebase.firestore.collection(CATEGORY_COLLECTION).document(category).set(mapOf(DATA to categoryNewData))
            }
        }
    }

    suspend fun updateCategory(category: String, documentReference: DocumentReference) {
        val categoryData = Firebase.firestore.collection(CATEGORY_COLLECTION).document(category).get().await().data?.get(DATA) as List<DocumentReference>?
        var categoryNewData = categoryData?.toMutableList() ?: mutableListOf()
        categoryNewData.add(documentReference)
        categoryNewData = categoryNewData.distinctBy { it.id }.toMutableList()
        Firebase.firestore.collection(CATEGORY_COLLECTION).document(category).set(mapOf(DATA to categoryNewData))
    }

    companion object {
        const val COUNT = "count"
        const val TOTAL_PAGE = "totalPages"
    }
}