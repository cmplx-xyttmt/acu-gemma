package com.example.acugemma.data

data class LessonStep(
    val content: String, // The teaching content for this step
    val question: String, // The question to ask the student
    val expectedAnswer: String // The expected answer (for validation)
)
