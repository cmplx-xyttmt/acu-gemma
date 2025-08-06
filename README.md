# AcuGemma

AcuGemma is an Android application designed to provide personalized, interactive AI tutoring for primary school students. Leveraging the Gemma AI model directly on-device, AcuGemma offers guided lessons in subjects like Mathematics and Geography, ensuring an engaging and accessible learning experience even offline.

## Video Demo Link

[**Place your video demo link here**]

## Technical Overview

AcuGemma integrates the Gemma 3n large language model directly onto the Android device using MediaPipe's `LlmInference` and `LlmInferenceSession`. This on-device execution offers significant advantages: enhanced user privacy as data remains local, rapid response times due to the elimination of network latency, and full offline capability. The `.tflite` model file is bundled with the application assets, enabling seamless deployment and immediate availability upon app installation.

The core innovation lies in its **dynamic prompting system**, which ensures a highly guided and effective tutoring experience. Lessons are structured into discrete `LessonStep` objects, each containing teaching material, a specific question, and an expected answer. The application employs a two-phase prompting system for each user interaction:

1.  **Initial Prompt (Lesson Introduction):** Introduces the step's content and asks its corresponding question.
2.  **Response Evaluation and Progression Prompt:** Evaluates the student's answer using the Gemma model itself (which responds with "CORRECT" or "INCORRECT"). Based on this validation, the AI provides appropriate feedback (praise or hints) and guides the student to the next lesson step or re-asks the current question.

This dynamic approach, combined with robust state management in the `LessonViewModel`, ensures consistent, accurate, and pedagogically sound interactions.

## Setup/Build Instructions

To set up and build the AcuGemma application, follow these steps:

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/your-repo/AcuGemma.git
    cd AcuGemma
    ```
    (Replace `https://github.com/your-repo/AcuGemma.git` with the actual repository URL)

2.  **Open in Android Studio:**
    *   Launch Android Studio.
    *   Select `File > Open` and navigate to the cloned `AcuGemma` directory.
    *   Click `Open`.

3.  **Sync Gradle Project:**
    *   Android Studio will automatically try to sync the Gradle project. If it doesn't, click the "Sync Project with Gradle Files" button (usually an elephant icon in the toolbar).
    *   Ensure you have a stable internet connection for Gradle to download necessary dependencies.

4.  **Install SDK Components:**
    *   Android Studio might prompt you to install missing SDK components (e.g., platform SDKs, build tools). Follow the prompts to install them.

5.  **Build the Application:**
    *   Once Gradle sync is complete and all SDK components are installed, you can build the application.
    *   Go to `Build > Make Project` or click the "Build" icon (hammer icon).

6.  **Run on Device/Emulator:**
    *   Connect an Android device via USB (ensure USB debugging is enabled) or start an Android Emulator.
    *   Select your device/emulator from the target dropdown in Android Studio.
    *   Click the "Run" button (green play icon) to deploy and run the app.

## File Structure

Key files and their organization:

*   `app/src/main/java/com/example/acugemma/`: The root of the application's Kotlin source code.
    *   `ai/`: Contains AI-related logic.
        *   `GemmaAiService.kt`: Handles interaction with the Gemma model, including initialization, response generation, and lifecycle management.
        *   `LessonPrompts.kt`: Contains functions for building dynamic prompts for the Gemma model, including the `buildDynamicPrompt` and `buildValidationPrompt`.
    *   `data/`: Defines data models and lesson content.
        *   `LessonStep.kt`: Data class defining the structure of a single step in a lesson.
        *   `LessonContent.kt`: Centralizes access to all lesson content, mapping `topicId` to specific lesson step lists.
        *   `data.math/`: Package containing lesson content for mathematics.
            *   `AdditionLessonContent.kt`: Defines the steps for the addition lesson.
            *   `MultiplicationDivisionLessonContent.kt`: Defines the steps for multiplication and division lessons.
            *   `ShapesLessonContent.kt`: Defines the steps for shapes lessons.
            *   `MoneyLessonContent.kt`: Defines the steps for money lessons.
            *   `TimeLessonContent.kt`: Defines the steps for time lessons.
        *   `data.geography/`: Package containing lesson content for geography.
            *   `GeographyLessonContent.kt`: Defines the steps for countries, continents, oceans, weather, maps, and capitals lessons.
    *   `ui/`: Contains UI-related components and screens.
        *   `screens/LessonScreen.kt`: The main Composable screen for displaying and interacting with lessons.
        *   `viewmodels/LessonViewModel.kt`: Manages the UI state for lessons, orchestrates AI interactions, and tracks student progress.
*   `app/src/main/assets/`: Contains the `gemma.tflite` model file.
*   `kaggle/writeup.md`: Detailed technical writeup of the project.
*   `guided_lessons_plan.md`: The development plan for implementing guided lessons.

## Planned Improvements

### Improving Context Management
Currently, each AI interaction is largely self-contained, with the model's "memory" limited to the immediate prompt. Future improvements will focus on implementing more sophisticated context management to enable richer, more coherent conversations over longer periods. This could involve:
*   **Limited Conversation History:** Passing a condensed history of previous turns (e.g., the last N messages or a summary of key points) to the AI with each new prompt.
*   **Summarization Techniques:** Exploring techniques to summarize past conversation segments to keep the context within the model's token limits.
*   **Leveraging `LlmInferenceSession` Features:** Investigating and utilizing any built-in context management features or best practices provided by the MediaPipe `LlmInferenceSession` API.

### Improving the Quality of Lessons
The current lesson content is structured but can be enhanced to provide an even more engaging and effective learning experience:
*   **Adaptive Learning Paths:** Develop logic to dynamically adjust lesson difficulty or introduce remedial steps based on a student's performance and identified areas of struggle.
*   **More Diverse Question Types:** Expand beyond simple expected answers to support numerical ranges, multiple correct phrases, or more open-ended questions that require nuanced AI evaluation.
*   **Rich Media Integration:** Incorporate images, audio clips, or short videos directly within `LessonStep` content to make lessons more interactive and appealing.

### Adding More Subjects and Lessons
To maximize AcuGemma's impact, a significant expansion of its content library is planned:
*   **New Subjects:** Introduce additional subjects such as Science, History, Language Arts, or even basic coding concepts.
*   **Deeper Content:** Develop more lessons within existing subjects, covering a wider range of topics and difficulty levels to cater to a broader age group and learning progression.
*   **Content Creation Pipeline:** Establish a streamlined process for creating, testing, and integrating new lesson content, potentially exploring community contributions or automated content generation tools.
