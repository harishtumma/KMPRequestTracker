package com.example.kmprequesttracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kmprequesttracker.R
import com.example.kmprequesttracker.domain.model.Attachment
import com.example.kmprequesttracker.domain.model.RequestOutcome
import com.example.kmprequesttracker.domain.model.StatusHistory
import com.example.kmprequesttracker.domain.model.UserRequest
import com.example.kmprequesttracker.presentation.RequestDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    state: RequestDetailUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.request_details)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (state) {
                is RequestDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is RequestDetailUiState.Success -> {
                    RequestDetailContent(state.request)
                }
                is RequestDetailUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message)
                    }
                }
                is RequestDetailUiState.NotFound -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Request not found")
                    }
                }
            }
        }
    }
}

@Composable
fun RequestDetailContent(request: UserRequest) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            RequestInfoSection(request)
        }
        
        val outcome = request.outcome
        if (outcome != null) {
            item {
                OutcomeSection(outcome)
            }
        }
        
        item {
            Text(
                text = stringResource(R.string.history_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(request.history) { historyItem ->
            StatusHistoryItem(historyItem)
        }
        
        if (request.attachments.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.attachments_label),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            items(request.attachments) { attachment ->
                AttachmentItem(attachment)
            }
        }
    }
}

@Composable
fun RequestInfoSection(request: UserRequest) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = request.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = request.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.status_label), style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.width(8.dp))
                StatusBadge(request.currentStatus)
            }
        }
    }
}

@Composable
fun StatusHistoryItem(item: StatusHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusBadge(item.status)
                Text(text = item.timestamp.toString().substring(0, 10), style = MaterialTheme.typography.labelSmall)
            }
            val note = item.note
            if (!note.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AttachmentItem(attachment: Attachment) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = attachment.name, modifier = Modifier.weight(1f))
            Text(text = attachment.type, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun OutcomeSection(outcome: RequestOutcome) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.outcome_label),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = outcome.result, style = MaterialTheme.typography.titleMedium)
            Text(text = outcome.remarks, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
