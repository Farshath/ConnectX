package com.farshath.connectx.data.repository

import com.farshath.connectx.domain.model.Notification
import com.farshath.connectx.domain.repository.NotificationRepository
import com.farshath.connectx.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotificationRepository {

    override fun getNotifications(userId: String): Flow<Resource<List<Notification>>> = callbackFlow {
        trySend(Resource.Loading())
        val listener = firestore.collection("notifications")
            .whereEqualTo("toUserId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val notifications = snapshot?.toObjects(Notification::class.java) ?: emptyList()
                trySend(Resource.Success(notifications))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun markAsRead(notificationId: String) {
        try {
            firestore.collection("notifications").document(notificationId)
                .update("isRead", true).await()
        } catch (e: Exception) {
            // Log error
        }
    }
}
