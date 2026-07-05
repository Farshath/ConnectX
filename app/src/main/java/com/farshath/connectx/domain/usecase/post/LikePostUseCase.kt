package com.farshath.connectx.domain.usecase.post

import com.farshath.connectx.domain.repository.PostRepository
import com.farshath.connectx.utils.Resource
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String, userId: String): Resource<Boolean> {
        return repository.likePost(postId, userId)
    }
}
