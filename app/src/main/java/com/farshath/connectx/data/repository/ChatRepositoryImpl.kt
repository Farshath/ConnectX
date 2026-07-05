package com.farshath.connectx.data.repository

import com.farshath.connectx.domain.model.Message
import com.farshath.connectx.domain.repository.ChatRepository
import com.farshath.connectx.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChatRepository {

    override fun getMessages(currentUserId: String, otherUserId: String): Flow<Resource<List<Message>>> = callbackFlow {
        trySend(Resource.Loading())
        val chatId = getChatId(currentUserId, otherUserId)
        val listener = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.localizedMessage ?: "Unknown error"))
                    return@addSnapshotListener
                }
                val messages = snapshot?.toObjects(Message::class.java) ?: emptyList()
                trySend(Resource.Success(messages))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun sendMessage(message: Message): Resource<Boolean> {
        return try {
            val chatId = getChatId(message.senderId, message.receiverId)
            val docRef = firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .document()
            val newMessage = message.copy(id = docRef.id)
            docRef.set(newMessage).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    private fun getChatId(uid1: String, uid2: String): String {
        return if (uid1 < uid2) "${uid1}_$uid2" else "${uid2}_$uid1"
    }
}
