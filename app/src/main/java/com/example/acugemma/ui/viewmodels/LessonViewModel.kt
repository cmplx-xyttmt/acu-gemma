package com.example.acugemma.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acugemma.ai.GemmaAiService
import com.example.acugemma.ai.LessonPrompts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.acugemma.data.LessonContent
import com.example.acugemma.data.LessonStep
import kotlinx.coroutines.launch

class LessonViewModel(private val gemmaAiService: GemmaAiService, private val topicId: String) : ViewModel() {

    private val _uiState = MutableStateFlow<LessonUiState>(LessonUiState.Processing(emptyList()))
    val uiState: StateFlow<LessonUiState> = _uiState

    private val _currentLessonIndex = MutableStateFlow(0)
    val currentLessonIndex: StateFlow<Int> = _currentLessonIndex

    private var lessonSteps: List<LessonStep>? = null

    init {
        viewModelScope.launch {
            try {
                gemmaAiService.initialize()
                lessonSteps = LessonContent.getLessonSteps(topicId)

                if (lessonSteps.isNullOrEmpty()) {
                    _uiState.value = LessonUiState.Error("No lesson content found for topic: $topicId")
                } else {
                    val initialStep = lessonSteps!![_currentLessonIndex.value]
                    val prompt = LessonPrompts.buildDynamicPrompt(initialStep)
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
            } catch (e: Exception) {
                _uiState.value = LessonUiState.Error("Failed to initialize model: ${e.message}")
            }
        }
    }

    fun sendMessage(message: String) {
        val currentMessages = (_uiState.value as? LessonUiState.Success)?.messages?.toMutableList() ?: mutableListOf()
        currentMessages.add(Message(message, Sender.User))
        _uiState.value = LessonUiState.Processing(currentMessages)

        val currentStep = lessonSteps?.get(_currentLessonIndex.value)

        if (currentStep == null) {
            _uiState.value = LessonUiState.Error("Lesson content not loaded.")
            return
        }

        val isCorrect = message.equals(currentStep.expectedAnswer, ignoreCase = true)
        val nextIndex = _currentLessonIndex.value + 1
        val isLastStep = nextIndex >= (lessonSteps?.size ?: 0)

        val prompt = if (isCorrect) {
            if (isLastStep) {
                LessonPrompts.buildDynamicPrompt(currentStep, message) // AI will congratulate and ask to review/try another
            } else {
                _currentLessonIndex.value = nextIndex
                LessonPrompts.buildDynamicPrompt(lessonSteps!![_currentLessonIndex.value], message) // AI will praise and introduce next step
            }
        } else {
            LessonPrompts.buildDynamicPrompt(currentStep, message) // AI will hint and re-ask current question
        }

        val progressListener = { partialResult: String, done: Boolean ->
            val updatedMessages = (_uiState.value as? LessonUiState.Processing)?.messages?.toMutableList() ?: mutableListOf()
            if (updatedMessages.isEmpty() || updatedMessages.last().sender == Sender.User) {
                updatedMessages.add(Message(partialResult, Sender.Model))
            } else {
                updatedMessages[updatedMessages.lastIndex] = updatedMessages.last().copy(text = updatedMessages.last().text + partialResult)
            }
            _uiState.value = if (done) LessonUiState.Success(updatedMessages) else LessonUiState.Processing(updatedMessages)
        }
        gemmaAiService.generateResponseAsync(prompt, progressListener)
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
