package com.farshath.connectx.di

import com.farshath.connectx.domain.repository.NotificationRepository
import com.farshath.connectx.domain.usecase.notification.GetNotificationsUseCase
import com.farshath.connectx.domain.usecase.notification.MarkAsReadUseCase
import com.farshath.connectx.domain.usecase.notification.NotificationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationUseCases(repository: NotificationRepository): NotificationUseCases {
        return NotificationUseCases(
            getNotifications = GetNotificationsUseCase(repository),
            markAsRead = MarkAsReadUseCase(repository)
        )
    }
}
