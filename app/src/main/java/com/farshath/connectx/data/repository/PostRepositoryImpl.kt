package com.farshath.connectx.data.repository

import com.farshath.connectx.domain.model.Post
import com.farshath.connectx.domain.repository.PostRepository
import com.farshath.connectx.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : PostRepository {

    override fun getPosts(): Flow<Resource<List<Post>>> = callbackFlow {
        trySend(Resource.Loading())
        val listener = firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val posts = snapshot?.toObjects(Post::class.java) ?: emptyList()
                trySend(Resource.Success(posts))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun createPost(post: Post): Resource<Boolean> {
        return try {
            val docRef = firestore.collection("posts").document()
            val newPost = post.copy(id = docRef.id)
            docRef.set(newPost).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun likePost(postId: String, userId: String): Resource<Boolean> {
        // Implementation for liking post will involve a sub-collection or array update
        // Simplified for now
        return Resource.Success(true)
    }

    override suspend fun deletePost(postId: String): Resource<Boolean> {
        return try {
            firestore.collection("posts").document(postId).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
