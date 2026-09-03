package com.example.kmprequesttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmprequesttracker.di.DependencyContainer
import com.example.kmprequesttracker.presentation.RequestDetailViewModel
import com.example.kmprequesttracker.presentation.RequestListViewModel
import com.example.kmprequesttracker.ui.RequestDetailScreen
import com.example.kmprequesttracker.ui.RequestListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    var currentRequestId by remember { mutableStateOf<String?>(null) }

    if (currentRequestId == null) {
        val listViewModel: RequestListViewModel = viewModel {
            RequestListViewModel(DependencyContainer.getRequestsUseCase)
        }
        val state by listViewModel.uiState.collectAsStateWithLifecycle()
        
        RequestListScreen(
            state = state,
            onEvent = { listViewModel.onEvent(it) },
            onNavigateToDetail = { currentRequestId = it }
        )
    } else {
        val detailViewModel: RequestDetailViewModel = viewModel {
            RequestDetailViewModel(DependencyContainer.getRequestDetailUseCase)
        }
        
        // Load detail when entering the screen
        LaunchedEffect(currentRequestId) {
            currentRequestId?.let { detailViewModel.loadDetail(it) }
        }
        
        val state by detailViewModel.uiState.collectAsStateWithLifecycle()
        
        RequestDetailScreen(
            state = state,
            onBack = { currentRequestId = null }
        )
    }
}
