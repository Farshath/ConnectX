package com.farshath.connectx.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshath.connectx.domain.model.User
import com.farshath.connectx.domain.usecase.auth.AuthUseCases
import com.farshath.connectx.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _searchState = mutableStateOf<Resource<List<User>>>(Resource.Success(emptyList()))
    val searchState: State<Resource<List<User>>> = _searchState

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _searchState.value = Resource.Loading()
            _searchState.value = authUseCases.searchUsers(query)
        }
    }
}
