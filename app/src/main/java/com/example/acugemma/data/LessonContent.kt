package com.example.acugemma.data

object LessonContent {

    val additionLesson = listOf(
        LessonStep(
            content = "Addition means combining two numbers to find a total.",
            question = "If you have 🍎 and get another 🍎, how many apples do you have?",
            expectedAnswer = "2"
        ),
        LessonStep(
            content = "That's right! You just added. Now, let's try another one.",
            question = "If you have 3 🎈 and get 2 more, how many balloons do you have?",
            expectedAnswer = "5"
        ),
        LessonStep(
            content = "Excellent! So, 3 plus 2 equals 5. Addition helps us count things when we put them together.",
            question = "What is 4 + 1?",
            expectedAnswer = "5"
        )
    )

    fun getLessonSteps(topicId: String): List<LessonStep>? {
        return when (topicId) {
            "addition" -> additionLesson
            // Add other lessons here as they are defined
            else -> null
        }
    }
}