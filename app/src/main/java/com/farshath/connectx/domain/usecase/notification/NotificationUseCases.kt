package com.farshath.connectx.domain.usecase.notification

data class NotificationUseCases(
    val getNotifications: GetNotificationsUseCase,
    val markAsRead: MarkAsReadUseCase
)
