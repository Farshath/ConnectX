package com.farshath.connectx.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshath.connectx.domain.model.Notification
import com.farshath.connectx.domain.usecase.auth.AuthUseCases
import com.farshath.connectx.domain.usecase.notification.NotificationUseCases
import com.farshath.connectx.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationUseCases: NotificationUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _notificationsState = mutableStateOf<Resource<List<Notification>>>(Resource.Loading())
    val notificationsState: State<Resource<List<Notification>>> = _notificationsState

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            val user = authUseCases.getCurrentUser().first()
            if (user != null) {
                notificationUseCases.getNotifications(user.uid).onEach { result ->
                    _notificationsState.value = result
                }.launchIn(viewModelScope)
            } else {
                _notificationsState.value = Resource.Error("User not authenticated")
            }
        }
    }

    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            notificationUseCases.markAsRead(notificationId)
        }
    }
}
