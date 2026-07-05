package com.farshath.connectx.domain.usecase.auth

import com.farshath.connectx.domain.model.User
import com.farshath.connectx.domain.repository.AuthRepository
import com.farshath.connectx.utils.Resource
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(query: String): Resource<List<User>> {
        if (query.isBlank()) return Resource.Success(emptyList())
        return repository.searchUsers(query)
    }
}
