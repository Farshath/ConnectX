package com.farshath.connectx.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshath.connectx.domain.model.Message
import com.farshath.connectx.domain.usecase.auth.AuthUseCases
import com.farshath.connectx.domain.usecase.chat.ChatUseCases
import com.farshath.connectx.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val authUseCases: AuthUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messagesState = mutableStateOf<Resource<List<Message>>>(Resource.Loading())
    val messagesState: State<Resource<List<Message>>> = _messagesState

    private val receiverId: String = checkNotNull(savedStateHandle["userId"])
    private var currentUserId: String = ""

    init {
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            val user = authUseCases.getCurrentUser().first()
            if (user != null) {
                currentUserId = user.uid
                chatUseCases.getMessages(currentUserId, receiverId).onEach { result ->
                    _messagesState.value = result
                }.launchIn(viewModelScope)
            }
        }
    }

    fun sendMessage(text: String) {
        if (currentUserId.isEmpty()) return
        viewModelScope.launch {
            val message = Message(
                senderId = currentUserId,
                receiverId = receiverId,
                text = text,
                timestamp = System.currentTimeMillis()
            )
            chatUseCases.sendMessage(message)
        }
    }
}
