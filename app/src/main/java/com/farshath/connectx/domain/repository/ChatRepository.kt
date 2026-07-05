package com.farshath.connectx.domain.repository

import com.farshath.connectx.domain.model.Message
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessages(currentUserId: String, otherUserId: String): Flow<Resource<List<Message>>>
    suspend fun sendMessage(message: Message): Resource<Boolean>
}
