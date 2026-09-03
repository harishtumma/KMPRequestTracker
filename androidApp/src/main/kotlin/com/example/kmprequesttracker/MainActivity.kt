package com.example.kmprequesttracker

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmprequesttracker.di.DependencyContainer
import com.example.kmprequesttracker.presentation.RequestDetailViewModel
import com.example.kmprequesttracker.presentation.RequestListViewModel
import com.example.kmprequesttracker.ui.RequestDetailScreen
import com.example.kmprequesttracker.ui.RequestListScreen
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            var localeCode by remember { mutableStateOf("en") }
            
            val context = LocalContext.current
            val locale = Locale(localeCode)
            Locale.setDefault(locale)
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            
            val localizedContext = context.createConfigurationContext(config)
            val layoutDirection = if (localeCode == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr

            CompositionLocalProvider(
                LocalContext provides localizedContext,
                LocalLayoutDirection provides layoutDirection
            ) {
                AppNavigation(
                    currentLanguage = localeCode,
                    onLanguageChange = { localeCode = if (it) "ar" else "en" }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    currentLanguage: String,
    onLanguageChange: (Boolean) -> Unit
) {
    var currentRequestId by remember { mutableStateOf<String?>(null) }

    if (currentRequestId == null) {
        val listViewModel: RequestListViewModel = viewModel {
            RequestListViewModel(DependencyContainer.getRequestsUseCase)
        }
        val state by listViewModel.uiState.collectAsStateWithLifecycle()
        
        RequestListScreen(
            state = state,
            onEvent = { listViewModel.onEvent(it) },
            onNavigateToDetail = { currentRequestId = it },
            isArabic = currentLanguage == "ar",
            onLanguageToggle = onLanguageChange
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
            onBack = { currentRequestId = null },
            isArabic = currentLanguage == "ar",
            onLanguageToggle = onLanguageChange
        )
    }
}
