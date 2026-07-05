package com.farshath.connectx.domain.usecase.auth

data class AuthUseCases(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val logout: LogoutUseCase,
    val getCurrentUser: GetCurrentUserUseCase,
    val isUserAuthenticated: IsUserAuthenticatedUseCase,
    val searchUsers: SearchUsersUseCase
)
