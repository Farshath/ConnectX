package com.farshath.connectx.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshath.connectx.domain.model.Post
import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.domain.repository.PostRepository
import com.farshath.connectx.utils.Resource
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _createPostState = mutableStateOf<Resource<Boolean>?>(null)
    val createPostState: State<Resource<Boolean>?> = _createPostState

    fun createPost(caption: String, imageUri: Uri?) {
        viewModelScope.launch {
            _createPostState.value = Resource.Loading()
            try {
                val user = authRepository.getCurrentUser().first()
                if (user == null) {
                    _createPostState.value = Resource.Error("User not logged in")
                    return@launch
                }

                var imageUrl = ""
                if (imageUri != null) {
                    val fileName = UUID.randomUUID().toString()
                    val ref = storage.reference.child("posts/$fileName")
                    ref.putFile(imageUri).await()
                    imageUrl = ref.downloadUrl.await().toString()
                }

                val post = Post(
                    userId = user.uid,
                    username = user.username,
                    userProfilePicture = user.profilePictureUrl,
                    caption = caption,
                    imageUrl = imageUrl,
                    timestamp = System.currentTimeMillis()
                )

                _createPostState.value = postRepository.createPost(post)
            } catch (e: Exception) {
                _createPostState.value = Resource.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
