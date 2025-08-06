package com.example.acugemma.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acugemma.ai.GemmaAiService
import com.example.acugemma.ai.LessonPrompts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LessonViewModel(private val gemmaAiService: GemmaAiService, private val topicId: String) : ViewModel() {

        private val _uiState = MutableStateFlow<LessonUiState>(LessonUiState.Processing(emptyList()))
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
        _uiState.value = LessonUiState.Processing(emptyList())
        val progressListener = { partialResult: String, done: Boolean ->
            val currentMessages = (_uiState.value as? LessonUiState.Success)?.messages?.toMutableList() ?: (_uiState.value as? LessonUiState.Processing)?.messages?.toMutableList() ?: mutableListOf()
            if (currentMessages.isEmpty() || currentMessages.last().sender == Sender.User) {
                currentMessages.add(Message(partialResult, Sender.Model))
            } else {
                currentMessages[currentMessages.lastIndex] = currentMessages.last().copy(text = currentMessages.last().text + partialResult)
            }
            _uiState.value = if (done) LessonUiState.Success(currentMessages) else LessonUiState.Processing(currentMessages)
        }
        gemmaAiService.generateResponseAsync(prompt, progressListener)
    }

    fun sendMessage(message: String) {
        val currentMessages = (_uiState.value as? LessonUiState.Success)?.messages?.toMutableList() ?: mutableListOf()
        currentMessages.add(Message(message, Sender.User))
        _uiState.value = LessonUiState.Processing(currentMessages)

        val progressListener = { partialResult: String, done: Boolean ->
            val updatedMessages = (_uiState.value as? LessonUiState.Processing)?.messages?.toMutableList() ?: mutableListOf()
            if (updatedMessages.isEmpty() || updatedMessages.last().sender == Sender.User) {
                updatedMessages.add(Message(partialResult, Sender.Model))
            } else {
                updatedMessages[updatedMessages.lastIndex] = updatedMessages.last().copy(text = updatedMessages.last().text + partialResult)
            }
            _uiState.value = if (done) LessonUiState.Success(updatedMessages) else LessonUiState.Processing(updatedMessages)
        }
        gemmaAiService.generateResponseAsync(message, progressListener)
    }

    override fun onCleared() {
        super.onCleared()
        gemmaAiService.cancelGeneration()
        gemmaAiService.close()
    }
}

sealed interface LessonUiState {
    data class Success(val messages: List<Message>) : LessonUiState
    data class Error(val message: String) : LessonUiState
    data class Processing(val messages: List<Message>) : LessonUiState
}

data class Message(val text: String, val sender: Sender)

enum class Sender {
    User, Model
}
