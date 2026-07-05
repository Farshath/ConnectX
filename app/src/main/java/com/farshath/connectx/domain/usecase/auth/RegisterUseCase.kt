package com.farshath.connectx.domain.usecase.auth

import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.utils.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, username: String): Resource<Boolean> {
        if (email.isBlank() || password.isBlank() || username.isBlank()) {
            return Resource.Error("Fields cannot be empty")
        }
        return repository.register(email, password, username)
    }
}
