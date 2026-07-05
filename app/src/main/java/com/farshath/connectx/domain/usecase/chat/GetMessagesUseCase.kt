package com.farshath.connectx.domain.usecase.chat

import com.farshath.connectx.domain.model.Message
import com.farshath.connectx.domain.repository.ChatRepository
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(currentUserId: String, otherUserId: String): Flow<Resource<List<Message>>> =
        repository.getMessages(currentUserId, otherUserId)
}
