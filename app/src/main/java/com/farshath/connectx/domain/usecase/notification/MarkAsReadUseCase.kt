package com.farshath.connectx.domain.usecase.notification

import com.farshath.connectx.domain.repository.NotificationRepository
import javax.inject.Inject

class MarkAsReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notificationId: String) = repository.markAsRead(notificationId)
}
