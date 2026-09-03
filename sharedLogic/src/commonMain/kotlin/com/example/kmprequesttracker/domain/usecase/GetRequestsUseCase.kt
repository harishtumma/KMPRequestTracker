package com.example.kmprequesttracker.domain.usecase

import com.example.kmprequesttracker.domain.model.UserRequest
import com.example.kmprequesttracker.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow

class GetRequestsUseCase(private val repository: RequestRepository) {
    operator fun invoke(): Flow<List<UserRequest>> {
        return repository.getRequests()
    }
}
