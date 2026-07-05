package com.farshath.connectx.di

import com.farshath.connectx.domain.repository.PostRepository
import com.farshath.connectx.domain.usecase.post.GetPostsUseCase
import com.farshath.connectx.domain.usecase.post.LikePostUseCase
import com.farshath.connectx.domain.usecase.post.PostUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository): PostUseCases {
        return PostUseCases(
            getPosts = GetPostsUseCase(repository),
            likePost = LikePostUseCase(repository)
        )
    }
}
