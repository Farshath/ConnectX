package com.farshath.connectx.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshath.connectx.domain.usecase.auth.AuthUseCases
import com.farshath.connectx.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _authState = mutableStateOf<Resource<Boolean>?>(null)
    val authState: State<Resource<Boolean>?> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            _authState.value = authUseCases.login(email, password)
        }
    }

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            _authState.value = authUseCases.register(email, password, username)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authUseCases.logout()
        }
    }

    fun isUserAuthenticated(): Boolean {
        return authUseCases.isUserAuthenticated()
    }

    fun getCurrentUser() = authUseCases.getCurrentUser()
}
