package com.farshath.connectx.di

import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.domain.usecase.auth.AuthUseCases
import com.farshath.connectx.domain.usecase.auth.GetCurrentUserUseCase
import com.farshath.connectx.domain.usecase.auth.IsUserAuthenticatedUseCase
import com.farshath.connectx.domain.usecase.auth.LoginUseCase
import com.farshath.connectx.domain.usecase.auth.LogoutUseCase
import com.farshath.connectx.domain.usecase.auth.RegisterUseCase
import com.farshath.connectx.domain.usecase.auth.SearchUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            login = LoginUseCase(repository),
            register = RegisterUseCase(repository),
            logout = LogoutUseCase(repository),
            getCurrentUser = GetCurrentUserUseCase(repository),
            isUserAuthenticated = IsUserAuthenticatedUseCase(repository),
            searchUsers = SearchUsersUseCase(repository)
        )
    }
}
