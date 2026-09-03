package com.example.kmprequesttracker.data.repository

import com.example.kmprequesttracker.domain.model.*
import com.example.kmprequesttracker.domain.repository.RequestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

class MockRequestRepository : RequestRepository {

    private val now = Clock.System.now()

    private val mockRequests = listOf(
        UserRequest(
            id = "1",
            title = "Office Laptop Upgrade",
            description = "Requesting a MacBook Pro M3 for development work due to performance issues with the current Intel machine.",
            currentStatus = RequestStatus.IN_PROGRESS,
            createdAt = now.minus(5.days),
            history = listOf(
                StatusHistory(RequestStatus.PENDING, now.minus(5.days), "Request submitted"),
                StatusHistory(RequestStatus.IN_PROGRESS, now.minus(3.days), "Assigned to IT department")
            ),
            attachments = listOf(
                Attachment("a1", "Specs.pdf", "PDF", "https://example.com/specs.pdf"),
                Attachment("a2", "current_laptop.jpg", "IMAGE", "https://example.com/img.jpg")
            )
        ),
        UserRequest(
            id = "2",
            title = "Travel Reimbursement - Q3",
            description = "Reimbursement for client visit to Dubai in July 2026. All receipts are attached.",
            currentStatus = RequestStatus.COMPLETED,
            createdAt = now.minus(10.days),
            history = listOf(
                StatusHistory(RequestStatus.PENDING, now.minus(10.days), "Submitted"),
                StatusHistory(RequestStatus.APPROVED, now.minus(8.days), "Finance approved"),
                StatusHistory(RequestStatus.COMPLETED, now.minus(7.days), "Payment processed")
            ),
            attachments = listOf(
                Attachment("a3", "Receipts.zip", "ZIP", "https://example.com/receipts.zip")
            ),
            outcome = RequestOutcome(
                result = "Processed",
                remarks = "Amount of 5000 AED credited to account ending in 1234.",
                finalDate = now.minus(7.days)
            )
        ),
        UserRequest(
            id = "3",
            title = "Annual Leave Request",
            description = "Requesting 5 days leave from Oct 1st to Oct 5th for family vacation.",
            currentStatus = RequestStatus.REJECTED,
            createdAt = now.minus(2.days),
            history = listOf(
                StatusHistory(RequestStatus.PENDING, now.minus(2.days), "Waiting for manager approval"),
                StatusHistory(RequestStatus.REJECTED, now.minus(1.days), "Insufficient leave balance")
            ),
            attachments = emptyList(),
            outcome = RequestOutcome(
                result = "Rejected",
                remarks = "Please check your HR portal for available balance.",
                finalDate = now.minus(1.days)
            )
        )
    )

    override fun getRequests(): Flow<List<UserRequest>> = flow {
        delay(1000) // Simulate network delay
        emit(mockRequests)
    }

    override fun getRequestById(id: String): Flow<UserRequest?> = flow {
        delay(500) // Simulate network delay
        emit(mockRequests.find { it.id == id })
    }
}
