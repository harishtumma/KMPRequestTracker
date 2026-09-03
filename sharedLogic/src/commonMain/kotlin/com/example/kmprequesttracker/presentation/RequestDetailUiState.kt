package com.example.kmprequesttracker.presentation

import com.example.kmprequesttracker.domain.model.UserRequest

sealed interface RequestDetailUiState {
    data object Loading : RequestDetailUiState
    data class Success(val request: UserRequest) : RequestDetailUiState
    data class Error(val message: String) : RequestDetailUiState
    data object NotFound : RequestDetailUiState
}
