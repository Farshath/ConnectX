package com.farshath.connectx.domain.repository

import com.farshath.connectx.domain.model.Notification
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(userId: String): Flow<Resource<List<Notification>>>
    suspend fun markAsRead(notificationId: String)
}
