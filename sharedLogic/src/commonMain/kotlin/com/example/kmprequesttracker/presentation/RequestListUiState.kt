package com.example.kmprequesttracker.presentation

import com.example.kmprequesttracker.domain.model.UserRequest

sealed interface RequestListUiState {
    data object Loading : RequestListUiState
    data class Success(val requests: List<UserRequest>) : RequestListUiState
    data class Error(val message: String) : RequestListUiState
    data object Empty : RequestListUiState
}

sealed interface RequestListEvent {
    data object Refresh : RequestListEvent
    data object Retry : RequestListEvent
}
