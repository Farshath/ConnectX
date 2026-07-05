package com.farshath.connectx.domain.usecase.auth

import com.farshath.connectx.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.logout()
}
