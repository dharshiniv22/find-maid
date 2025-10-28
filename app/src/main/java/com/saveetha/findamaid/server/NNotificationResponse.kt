package com.saveetha.findamaid.server

data class NNotificationResponse(
    val status: Boolean,
    val notifications: List<NotificationItem>
)
