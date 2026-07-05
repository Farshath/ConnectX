package com.farshath.connectx.domain.usecase.auth

import com.farshath.connectx.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.isUserAuthenticated()
}
