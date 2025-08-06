Lesson Plans Strategy: Guided Workflow for AcuGemma
This document outlines the strategy for implementing a highly guided and interactive learning experience within the AcuGemma application, leveraging dynamic prompting with pre-prepared content. This approach is crucial for ensuring accuracy, consistency, and effective tutoring, especially when working with smaller language models like Gemma 3n.

Why a Guided Workflow Is a Great Idea
Relying solely on the model's internal knowledge for every step of a lesson can lead to several challenges:

Inconsistency: The model might present the same concepts differently across sessions or users, or its explanations might not consistently align with the app's pedagogical goals.

Off-topic responses: Smaller models can easily deviate from the lesson plan if a user's question or comment takes the conversation in an unexpected direction.

Hallucinations: There's a risk of the model generating incorrect or nonsensical information, which is unacceptable in an educational context where accuracy is paramount.

By implementing a guided workflow with pre-prepared content, we gain:

Accuracy and Reliability: All factual information and core explanations are controlled and verified.

Consistency: The learning path and teaching style remain uniform across all users and sessions.

Focused Interaction: The model is explicitly guided on what to teach and what questions to ask, keeping the conversation on track.

Effective Learning: Breaking down content into small, interactive chunks improves comprehension and retention.

How to Implement a Guided Workflow
The implementation of this strategy involves three primary components: a structured data model for your lesson content, a mechanism for tracking the student's progress within a lesson, and a function responsible for dynamically constructing prompts for the AI model.

Step 1: Structure Your Lesson Data
Lesson content will be organized into a series of discrete steps. Each step will contain the necessary teaching material, the question to be posed to the student, and the expected answer for validation. This can be represented using a Kotlin data class.

// A single step in a lesson
data class LessonStep(
val content: String, // The teaching content for this step
val question: String, // The question to ask the student
val expectedAnswer: String // The expected answer (for validation)
)

// Example: The full lesson for Addition as a list of steps
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
// ... more steps for the lesson
)

Step 2: Track the Student's Progress
Within your application's state management (e.g., a ViewModel), you will maintain a variable that indicates the student's current position within the lesson. A simple integer representing the index of the current LessonStep is sufficient.

// Example state in your ViewModel (using Compose's mutableStateOf)
class ChatViewModel {
var currentLessonIndex by mutableStateOf(0)
// ... other chat-related state, like conversation history
}

This currentLessonIndex will be updated as the student progresses through the lesson steps.

Step 3: Build a Dynamic Prompt
This is the core mechanism for guiding the AI. Instead of a single, static prompt for an entire lesson, a dynamic prompt will be constructed for each turn of the conversation. This prompt will combine the overarching tutor persona with the specific content and question from the currentLessonIndex, and it will also incorporate the user's previous response to guide the AI's feedback.

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

Step 4: Integrate into the Chat Flow (Conceptual)
The chat loop logic will be updated to:

Initialization: When a lesson begins, retrieve the LessonStep corresponding to currentLessonIndex (initially 0). Call buildDynamicPrompt(currentLessonStep) (without userResponse) to get the initial AI message. Send this to Gemma 3n.

User Input: When the user sends a message, capture it.

Evaluate & Advance/Hint:

Compare the user's message with currentLessonStep.expectedAnswer.

If correct, increment currentLessonIndex.

Retrieve the next LessonStep (if currentLessonIndex is still within bounds).

Call buildDynamicPrompt(currentLessonStep, userResponse) to generate the AI's response, which will include feedback based on correctness and either the next question or a hint.

Send to Model: Send the dynamically generated prompt to Gemma 3n.

Display: Display Gemma's response.

This dynamic prompting system ensures that the AI's responses are always relevant to the current lesson step, provide appropriate feedback, and guide the student effectively through the pre-defined curriculum.
