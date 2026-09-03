package com.example.kmprequesttracker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kmprequesttracker.R
import com.example.kmprequesttracker.domain.model.RequestStatus
import com.example.kmprequesttracker.domain.model.UserRequest
import com.example.kmprequesttracker.presentation.RequestListEvent
import com.example.kmprequesttracker.presentation.RequestListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
    state: RequestListUiState,
    isArabic: Boolean,
    onLanguageToggle: (Boolean) -> Unit,
    onEvent: (RequestListEvent) -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.requests_title)) },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = if (isArabic) "AR" else "EN",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Switch(
                            checked = isArabic,
                            onCheckedChange = onLanguageToggle,
                            modifier = Modifier.scale(0.8f)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (state) {
                is RequestListUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is RequestListUiState.Success -> {
                    RequestList(state.requests, onNavigateToDetail)
                }
                is RequestListUiState.Error -> {
                    ErrorView(state.message) { onEvent(RequestListEvent.Retry) }
                }
                is RequestListUiState.Empty -> {
                    EmptyView()
                }
            }
        }
    }
}

@Composable
fun RequestList(requests: List<UserRequest>, onItemClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(requests, key = { it.id }) { request ->
            RequestItem(request, onItemClick)
        }
    }
}

@Composable
fun RequestItem(request: UserRequest, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(request.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = request.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.status_label), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(4.dp))
                StatusBadge(request.currentStatus)
            }
        }
    }
}

@Composable
fun StatusBadge(status: RequestStatus) {
    val color = when (status) {
        RequestStatus.PENDING -> Color.Gray
        RequestStatus.IN_PROGRESS -> Color.Blue
        RequestStatus.APPROVED -> Color.Green
        RequestStatus.REJECTED -> Color.Red
        RequestStatus.COMPLETED -> Color.DarkGray
    }
    
    val statusTextRes = when (status) {
        RequestStatus.PENDING -> R.string.status_pending
        RequestStatus.IN_PROGRESS -> R.string.status_in_progress
        RequestStatus.APPROVED -> R.string.status_approved
        RequestStatus.REJECTED -> R.string.status_rejected
        RequestStatus.COMPLETED -> R.string.status_completed
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
        border = AssistChipDefaults.assistChipBorder(enabled = true)
    ) {
        Text(
            text = stringResource(statusTextRes),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color
        )
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.error_loading))
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry_button))
        }
    }
}

@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.empty_requests))
    }
}
