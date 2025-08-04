package com.example.acugemma.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acugemma.ai.GemmaAiService
import com.example.acugemma.ai.LessonPrompts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel(private val gemmaAiService: GemmaAiService, private val topicId: String) : ViewModel() {

    private val _uiState = MutableStateFlow<LessonUiState>(LessonUiState.Loading)
    val uiState: StateFlow<LessonUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                gemmaAiService.initialize()
                startLesson()
            } catch (e: Exception) {
                _uiState.value = LessonUiState.Error("Failed to initialize model: ${e.message}")
            }
        }
    }

    private fun startLesson() {
        val prompt = LessonPrompts.getPrompt(topicId)
        val progressListener = { partialResult: String, done: Boolean ->
            val currentMessages = (uiState.value as? LessonUiState.Success)?.messages ?: emptyList()
            val newMessages = if (currentMessages.isEmpty()) {
                listOf(partialResult)
            } else {
                currentMessages.toMutableList().apply { set(lastIndex, get(lastIndex) + partialResult) }
            }
            _uiState.value = LessonUiState.Success(newMessages)
        }
        gemmaAiService.generateResponseAsync(prompt, progressListener)
    }

    fun sendMessage(message: String) {
        val currentMessages = (uiState.value as? LessonUiState.Success)?.messages ?: emptyList()
        val progressListener = { partialResult: String, done: Boolean ->
            val newMessages = if (currentMessages.isEmpty()) {
                listOf(partialResult)
            } else {
                currentMessages.toMutableList().apply { set(lastIndex, get(lastIndex) + partialResult) }
            }
            _uiState.value = LessonUiState.Success(newMessages)
        }
        gemmaAiService.generateResponseAsync(message, progressListener)
    }

    override fun onCleared() {
        super.onCleared()
        gemmaAiService.close()
    }
}

sealed interface LessonUiState {
    object Loading : LessonUiState
    data class Success(val messages: List<String>) : LessonUiState
    data class Error(val message: String) : LessonUiState
}
