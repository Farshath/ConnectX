package com.farshath.connectx.di

import com.farshath.connectx.domain.repository.ChatRepository
import com.farshath.connectx.domain.usecase.chat.ChatUseCases
import com.farshath.connectx.domain.usecase.chat.GetMessagesUseCase
import com.farshath.connectx.domain.usecase.chat.SendMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            getMessages = GetMessagesUseCase(repository),
            sendMessage = SendMessageUseCase(repository)
        )
    }
}
