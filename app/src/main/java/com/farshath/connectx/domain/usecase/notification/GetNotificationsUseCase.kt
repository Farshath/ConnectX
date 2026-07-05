package com.farshath.connectx.domain.usecase.notification

import com.farshath.connectx.domain.model.Notification
import com.farshath.connectx.domain.repository.NotificationRepository
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(userId: String): Flow<Resource<List<Notification>>> = repository.getNotifications(userId)
}
