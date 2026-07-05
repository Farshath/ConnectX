package com.farshath.connectx.domain.usecase.chat

data class ChatUseCases(
    val getMessages: GetMessagesUseCase,
    val sendMessage: SendMessageUseCase
)
