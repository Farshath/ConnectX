package com.farshath.connectx.domain.repository

import com.farshath.connectx.domain.model.User
import com.farshath.connectx.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(email: String, password: String, username: String): Resource<Boolean>
    suspend fun login(email: String, password: String): Resource<Boolean>
    suspend fun logout()
    fun getCurrentUser(): Flow<User?>
    fun isUserAuthenticated(): Boolean
    suspend fun sendPasswordResetEmail(email: String): Resource<Boolean>
    suspend fun searchUsers(query: String): Resource<List<User>>
}
