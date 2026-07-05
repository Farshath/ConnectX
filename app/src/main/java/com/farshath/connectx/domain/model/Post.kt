package com.farshath.connectx.domain.model

data class Post(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val userProfilePicture: String = "",
    val caption: String = "",
    val imageUrl: String = "",
    val videoUrl: String? = null,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
