package com.farshath.connectx.domain.repository

import com.farshath.connectx.domain.model.Post
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
    suspend fun createPost(post: Post): Resource<Boolean>
    suspend fun likePost(postId: String, userId: String): Resource<Boolean>
    suspend fun deletePost(postId: String): Resource<Boolean>
}
