package com.example.kmprequesttracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmprequesttracker.domain.usecase.GetRequestsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RequestListViewModel(
    private val getRequestsUseCase: GetRequestsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RequestListUiState>(RequestListUiState.Loading)
    val uiState: StateFlow<RequestListUiState> = _uiState.asStateFlow()

    init {
        loadRequests()
    }

    fun onEvent(event: RequestListEvent) {
        when (event) {
            RequestListEvent.Refresh, RequestListEvent.Retry -> loadRequests()
        }
    }

    private fun loadRequests() {
        viewModelScope.launch {
            _uiState.value = RequestListUiState.Loading
            getRequestsUseCase()
                .catch { e ->
                    _uiState.value = RequestListUiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { requests ->
                    if (requests.isEmpty()) {
                        _uiState.value = RequestListUiState.Empty
                    } else {
                        _uiState.value = RequestListUiState.Success(requests)
                    }
                }
        }
    }
}
