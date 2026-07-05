package com.farshath.connectx.data.repository

import com.farshath.connectx.domain.model.User
import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String,
        username: String
    ): Resource<Boolean> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(
                uid = result.user?.uid ?: "",
                username = username,
                email = email
            )
            firestore.collection("users").document(user.uid).set(user).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun login(email: String, password: String): Resource<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val uid = firebaseAuth.currentUser?.uid
            if (uid != null) {
                firestore.collection("users").document(uid)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            close(error)
                            return@addSnapshotListener
                        }
                        val user = snapshot?.toObject(User::class.java)
                        trySend(user)
                    }
            } else {
                trySend(null)
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun searchUsers(query: String): Resource<List<User>> {
        return try {
            val result = firestore.collection("users")
                .whereGreaterThanOrEqualTo("username", query)
                .whereLessThanOrEqualTo("username", query + "\uf8ff")
                .get()
                .await()
            val users = result.toObjects(User::class.java)
            Resource.Success(users)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
