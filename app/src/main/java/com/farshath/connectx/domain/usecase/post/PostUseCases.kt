package com.farshath.connectx.domain.usecase.post

data class PostUseCases(
    val getPosts: GetPostsUseCase,
    val likePost: LikePostUseCase
)
