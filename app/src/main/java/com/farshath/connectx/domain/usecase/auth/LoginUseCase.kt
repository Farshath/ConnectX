package com.farshath.connectx.domain.usecase.auth

import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.utils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error("Fields cannot be empty")
        }
        return repository.login(email, password)
    }
}
