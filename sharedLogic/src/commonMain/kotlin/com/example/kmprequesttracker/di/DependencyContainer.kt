package com.example.kmprequesttracker.di

import com.example.kmprequesttracker.data.repository.MockRequestRepository
import com.example.kmprequesttracker.domain.repository.RequestRepository
import com.example.kmprequesttracker.domain.usecase.GetRequestDetailUseCase
import com.example.kmprequesttracker.domain.usecase.GetRequestsUseCase

object DependencyContainer {
    val repository: RequestRepository by lazy { MockRequestRepository() }
    val getRequestsUseCase by lazy { GetRequestsUseCase(repository) }
    val getRequestDetailUseCase by lazy { GetRequestDetailUseCase(repository) }
}
