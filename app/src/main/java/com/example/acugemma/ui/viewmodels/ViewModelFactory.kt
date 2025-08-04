package com.example.acugemma.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.acugemma.ai.GemmaAiService

class ViewModelFactory(private val gemmaAiService: GemmaAiService, private val topicId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            return LessonViewModel(gemmaAiService, topicId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
