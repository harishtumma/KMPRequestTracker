package com.example.kmprequesttracker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmprequesttracker.domain.usecase.GetRequestDetailUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RequestDetailViewModel(
    private val getRequestDetailUseCase: GetRequestDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RequestDetailUiState>(RequestDetailUiState.Loading)
    val uiState: StateFlow<RequestDetailUiState> = _uiState.asStateFlow()

    fun loadDetail(id: String) {
        viewModelScope.launch {
            _uiState.value = RequestDetailUiState.Loading
            getRequestDetailUseCase(id)
                .catch { e ->
                    _uiState.value = RequestDetailUiState.Error(e.message ?: "Unknown error")
                }
                .collect { request ->
                    if (request == null) {
                        _uiState.value = RequestDetailUiState.NotFound
                    } else {
                        _uiState.value = RequestDetailUiState.Success(request)
                    }
                }
        }
    }
}
