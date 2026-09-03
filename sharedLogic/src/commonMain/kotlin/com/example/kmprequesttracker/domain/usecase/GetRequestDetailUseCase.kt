package com.example.kmprequesttracker.domain.usecase

import com.example.kmprequesttracker.domain.model.UserRequest
import com.example.kmprequesttracker.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow

class GetRequestDetailUseCase(private val repository: RequestRepository) {
    operator fun invoke(id: String): Flow<UserRequest?> {
        return repository.getRequestById(id)
    }
}
