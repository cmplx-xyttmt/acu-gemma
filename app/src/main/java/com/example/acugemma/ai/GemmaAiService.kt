package com.example.acugemma.ai

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession
import com.google.mediapipe.tasks.genai.llminference.ProgressListener
import java.io.File

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GemmaAiService(private val context: Context) {

    private lateinit var llmInference: LlmInference
    private lateinit var llmSession: LlmInferenceSession

    suspend fun initialize() = withContext(Dispatchers.IO) {
        val modelName = "gemma-3n-E2B-it-int4.task"
        val cacheDir = context.cacheDir
        val modelFile = File(cacheDir, modelName)

        if (!modelFile.exists()) {
            context.assets.open(modelName).use { inputStream ->
                modelFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }

        val inferenceOptions = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(modelFile.absolutePath)
            .build()

        llmInference = LlmInference.createFromOptions(context, inferenceOptions)

        val sessionOptions = LlmInferenceSession.LlmInferenceSessionOptions.builder()
            .build()

        llmSession = LlmInferenceSession.createFromOptions(llmInference, sessionOptions)
    }

    fun generateResponseAsync(prompt: String, listener: ProgressListener<String>) {
        llmSession.addQueryChunk(prompt)
        llmSession.generateResponseAsync(listener)
    }

    fun close() {
        llmSession.close()
        llmInference.close()
    }
}


