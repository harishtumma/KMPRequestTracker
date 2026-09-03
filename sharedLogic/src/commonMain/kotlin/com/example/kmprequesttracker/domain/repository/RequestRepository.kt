package com.example.kmprequesttracker.domain.repository

import com.example.kmprequesttracker.domain.model.UserRequest
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    fun getRequests(): Flow<List<UserRequest>>
    fun getRequestById(id: String): Flow<UserRequest?>
}
