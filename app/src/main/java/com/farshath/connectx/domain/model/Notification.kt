package com.farshath.connectx.domain.model

data class Notification(
    val id: String = "",
    val toUserId: String = "",
    val fromUserId: String = "",
    val fromUsername: String = "",
    val fromUserProfilePicture: String = "",
    val type: String = "", // "like", "comment", "follow"
    val postId: String? = null,
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
