package com.farshath.connectx.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshath.connectx.domain.model.Post
import com.farshath.connectx.domain.usecase.post.PostUseCases
import com.farshath.connectx.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _postsState = mutableStateOf<Resource<List<Post>>>(Resource.Loading())
    val postsState: State<Resource<List<Post>>> = _postsState

    init {
        getPosts()
    }

    fun getPosts() {
        postUseCases.getPosts().onEach { result ->
            _postsState.value = result
        }.launchIn(viewModelScope)
    }

    fun likePost(postId: String, userId: String) {
        viewModelScope.launch {
            postUseCases.likePost(postId, userId)
        }
    }
}
