package com.wiseowl.woli.data.remote.firebase

import android.content.Context
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.domain.model.GuestUser
import com.wiseowl.woli.domain.model.Policy
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class FirebaseAPIService(private val context: Context) : RemoteAPIService {
    private val firestore = Firebase.firestore
    private val scope = CoroutineScope(Dispatcher.IO)
    override val userState = MutableStateFlow(GuestUser).apply {
        scope.launch { emit(getUserInfo() ?: GuestUser) }
    }

    override suspend fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
    ): Result<User> {
        if (isEmailRegistered(email)) return Result.Error(Error("Account already exists"))

        val result = Firebase.auth.createUserWithEmailAndPassword(
            email, password
        ).await()

        return if (result.user != null) {
            val user = User(firstName, lastName, result.user!!.uid, email, null) + GuestUser
            updateUser(user)
            Result.Success(user)
        } else {
            Result.Error(Error("Wrong credentials"))
        }
    }

    private fun updateUser(user: User?) {
        user?.let {
            GuestUser = user
            firestore.collection(USERS_COLLECTION).document(user.email).set(user)
        }
        userState.update { user ?: GuestUser }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = Firebase.auth.signInWithEmailAndPassword(
                email, password
            ).await()

            if (result.user == null) throw FirebaseAuthInvalidCredentialsException(
                "",
                "Invalid Credentials"
            )
            val user = getUser(email)
            if (user != null) {
                val updatedUser = user + GuestUser
                updateUser(updatedUser)
                Result.Success(updatedUser)
            } else Result.Error(Error("Something went wrong"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.Error(Error("Invalid Credentials"))
        }
    }

    private suspend fun getUser(email: String? = null): User?{
        return (Firebase.auth.currentUser?.email ?: email)?.let {
            firestore.collection(USERS_COLLECTION).document(it).get().await().data?.toUser()
        }
    }

    override suspend fun deleteUser() {
        ifLoggedIn {
            val email = Firebase.auth.currentUser?.email!!
            firestore.collection(USERS_COLLECTION).document(email).delete()
            Firebase.auth.currentUser?.delete()
            Firebase.auth.signOut()
            updateUser(null)
        }
    }

    override suspend fun isEmailRegistered(email: String): Boolean {
        return firestore.collection(USERS_COLLECTION).document(email).get().await().exists()
    }

    override suspend fun getUserInfo(): User {
        return ifLoggedIn {
            val email = Firebase.auth.currentUser?.email!!
            val user =
                firestore.collection(USERS_COLLECTION).document(email).get().await().data?.toUser()
            updateUser(user)
            user
        } ?: GuestUser
    }

    private inline fun <R : Any?> ifLoggedIn(onLoggedIn: () -> R): R? {
        return if (isLoggedIn()) onLoggedIn()
        else null
    }

    override suspend fun addToFavourites(mediaId: Long) {
        getUserInfo().let { user ->
            if (user.favourites?.contains(mediaId) != true) {
                val userUpdatedState = user.copy(favourites = user.favourites.orEmpty() + mediaId)
                if (user != GuestUser) {
                    val email = Firebase.auth.currentUser?.email
                    if (email != null) {
                        firestore.collection(USERS_COLLECTION).document(email).set(userUpdatedState)
                    }
                }
                updateUser(userUpdatedState)
            }
        }
    }

    override suspend fun removeFromFavourites(mediaId: Long) {
        getUserInfo().let { user ->
            if (user.favourites?.contains(mediaId) == true) {
                val newFavourite = user.favourites.filter { it != mediaId }
                val newUserState = user.copy(favourites = newFavourite)
                if(user!= GuestUser) {
                    val email = Firebase.auth.currentUser?.email
                    if (email != null) {
                        firestore.collection(USERS_COLLECTION).document(email).set(newUserState)
                    }
                }
                updateUser(newUserState)
            }
        }
    }

    override suspend fun getFavourites(): List<Long> {
        return userState.value.favourites.orEmpty()
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
        updateUser(null)
    }

    override suspend fun getPrivacyPolicyPage(): List<Policy>? {
        val result = firestore.collection(PRIVACY_POLICY_COLLECTION).getDocumentOrNull(DATA)?.data
        val policyData = result?.get(POLICIES) as List<Map<String, String>>?
        val policies = policyData?.map { it.toPolicy() }
        return policies
    }

    override fun isLoggedIn(): Boolean = Firebase.auth.currentUser != null

    override suspend fun getImageBitmap(url: String): Bitmap? {
        return withContext(Dispatcher.IO) {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request))
            result.image?.toBitmap()
        }
    }

    companion object {
        const val PRIVACY_POLICY_COLLECTION = "privacypolicy"
        const val USERS_COLLECTION = "users"
        const val POLICIES = "policies"
        const val DATA = "data"

        private fun Map<String, Any>.toUser(): User {
            return User(
                firstName = getValue(User::firstName.name).toString(),
                lastName = getValue(User::lastName.name).toString(),
                favourites = getValue(User::favourites.name) as List<Long>?,
                uid = getValue(User::uid.name).toString(),
                email = getValue(User::email.name).toString(),
            )
        }

        private fun Map<String, Any>.toPolicy(): Policy {
            return Policy(
                title = getValue(Policy::title.name).toString(),
                description = getValue(Policy::description.name).toString()
            )
        }

        suspend fun CollectionReference.getDocumentOrNull(documentId: String): DocumentSnapshot? {
            return try {
                document(documentId).get().await()
            } catch (e: Exception) {
                null
            }
        }
    }
}