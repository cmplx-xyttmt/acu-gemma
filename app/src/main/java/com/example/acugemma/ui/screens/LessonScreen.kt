package com.example.acugemma.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.acugemma.ai.GemmaAiService
import com.example.acugemma.ui.viewmodels.LessonViewModel
import com.example.acugemma.ui.viewmodels.ViewModelFactory
import com.example.acugemma.ui.viewmodels.LessonUiState

@Composable
fun LessonScreen(topicId: String) {
    val viewModel: LessonViewModel = viewModel(factory = ViewModelFactory(GemmaAiService(LocalContext.current), topicId))
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is LessonUiState.Loading -> {
                Text(text = "Loading...")
            }
            is LessonUiState.Success -> {
                LazyColumn {
                    items(state.messages) { message ->
                        Text(text = message)
                    }
                }
            }
            is LessonUiState.Error -> {
                Text(text = state.message)
            }
        }
    }
}
