package com.farshath.connectx.domain.usecase.chat

import com.farshath.connectx.domain.model.Message
import com.farshath.connectx.domain.repository.ChatRepository
import com.farshath.connectx.utils.Resource
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: Message): Resource<Boolean> {
        if (message.text.isBlank() && message.imageUrl == null) {
            return Resource.Error("Message cannot be empty")
        }
        return repository.sendMessage(message)
    }
}
