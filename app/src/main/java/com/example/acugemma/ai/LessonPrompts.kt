package com.example.acugemma.ai

import com.example.acugemma.data.LessonStep

object LessonPrompts {

    fun buildDynamicPrompt(step: LessonStep, userResponse: String? = null): String {
        val basePrompt = """
You are AcuGemma, a friendly and encouraging AI tutor. Your sole purpose is to teach the lesson provided below.

        Here is the current step of the lesson:
        ${step.content}
        
    """.trimIndent()

    val responsePrompt = if (userResponse != null) {
        // Build a prompt to evaluate the user's answer and guide the next AI response
        """
        The student's previous response was: "$userResponse".
        
        Is this answer correct? The expected answer is "${step.expectedAnswer}".
        
        If the student's answer is correct:
        - Praise them warmly.
        - If there is a next step in the lesson, introduce its content and ask its question.
        - If this is the last step, congratulate them on completing the lesson and ask if they want to review or try another lesson.
        
        If the student's answer is incorrect:
        - Say something encouraging like "That's a good try!" or "Almost!"
        - Provide a gentle hint related to the current step's content.
        - Ask them to try answering the current question again: "${step.question}".
        
        Your response must be short, focused, and directly follow these instructions.
        """.trimIndent()
    } else {
        // Build the prompt for the very first message of the step (to introduce content and ask the question)
        """
        Now, introduce the content for this step and ask the student the question: "${step.question}".
        Your response must be short and focused.
        """.trimIndent()
    }

    return basePrompt + responsePrompt
    }

    fun buildValidationPrompt(userResponse: String, expectedAnswer: String): String {
        return """
Is "$userResponse" correct? Expected: "$expectedAnswer". Respond CORRECT or INCORRECT.
""".trimIndent()
    }
}