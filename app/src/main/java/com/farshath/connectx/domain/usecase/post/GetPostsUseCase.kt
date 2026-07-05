package com.farshath.connectx.domain.usecase.post

import com.farshath.connectx.domain.model.Post
import com.farshath.connectx.domain.repository.PostRepository
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> = repository.getPosts()
}
