package com.example.kmprequesttracker.domain.model

import kotlinx.datetime.Instant

enum class RequestStatus {
    PENDING,
    IN_PROGRESS,
    APPROVED,
    REJECTED,
    COMPLETED
}

data class Attachment(
    val id: String,
    val name: String,
    val type: String,
    val url: String
)

data class StatusHistory(
    val status: RequestStatus,
    val timestamp: Instant,
    val note: String? = null
)

data class RequestOutcome(
    val result: String,
    val remarks: String,
    val finalDate: Instant
)

data class UserRequest(
    val id: String,
    val title: String,
    val description: String,
    val currentStatus: RequestStatus,
    val createdAt: Instant,
    val history: List<StatusHistory>,
    val attachments: List<Attachment>,
    val outcome: RequestOutcome? = null
)
