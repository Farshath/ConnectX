package com.farshath.connectx.domain.model

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val profilePictureUrl: String = "",
    val bio: String = "",
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val postCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
