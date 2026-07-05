package com.farshath.connectx.di

import com.farshath.connectx.data.repository.AuthRepositoryImpl
import com.farshath.connectx.data.repository.ChatRepositoryImpl
import com.farshath.connectx.data.repository.NotificationRepositoryImpl
import com.farshath.connectx.data.repository.PostRepositoryImpl
import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.domain.repository.ChatRepository
import com.farshath.connectx.domain.repository.NotificationRepository
import com.farshath.connectx.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
}
