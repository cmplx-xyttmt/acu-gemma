# AcuGemma Development Plan

## Project Goal
To develop an interactive Android application that leverages a local Gemma AI model to provide personalized and engaging educational lessons for primary school students.

## Current Status
- Basic lesson display with Gemma AI integration.
- Asynchronous model loading implemented to prevent UI freezes.
- Lesson screen UI updated for consistency with back navigation and bottom navigation bar (though functionality is not yet implemented).
- Basic chat interaction with the model is functional.
- Chat bubble design enhanced.
- Model introduction refined to be "AcuGemma".
- Keyboard overlap issue addressed.

## Remaining Tasks & Estimated Time (Approx. 20 hours)

### Phase 1: UI/UX Refinements & Model Feedback (Estimated: 5-8 hours)

*   **Task 1.1: Enhance Chat Bubble Design** (Completed)

*   **Task 1.2: Improve Prompts for Interactivity and Chatbot Name** (Partially completed, will be refined in Phase 3)

*   **Task 1.3: Implement Loading Spinner for Model Processing (2-3 hours)**
    *   **Description:** Introduce a visual loading indicator (e.g., a progress bar or spinner) that appears when the model is generating a response and disappears once the response is complete.
    *   **Files to Modify:** `app/src/main/java/com/example/acugemma/ui/screens/LessonScreen.kt`, `app/src/main/java/com/example/acugemma/ui/viewmodels/LessonViewModel.kt` (to expose a loading state).
*  **Task 1.4: Enable chat boxes to render markdown content.

### Phase 2: Navigation & Core Functionality (Estimated: 3-5 hours)

*   **Task 2.1: Implement Bottom Navigation Functionality (3-5 hours)**
    *   **Description:** enable proper navigation between different sections (Home, Progress, Subjects, Profile etc.) when a tab is clicked. This will involve defining the navigation routes for each tab and handling the `onTabSelected` callback.
   
### Phase 3: Model Behavior & Guardrails (Estimated: 10-15 hours)

*   **Task 3.1: Refine Initial Prompt for User-Driven Interaction (3-4 hours)**
    *   **Description:** Modify `LessonPrompts.kt` to make the model start by immediately asking a simple question to assess the user's current knowledge of the topic, rather than providing a lengthy introduction. The prompt will explicitly instruct the model to *not* acknowledge the prompt itself.
    *   **Files to Modify:** `app/src/main/java/com/example/acugemma/ai/LessonPrompts.kt`

*   **Task 3.2: Implement Model Response Logic (Correct/Ask Question) (4-6 hours)**
    *   **Description:** This will involve more advanced prompt engineering within `LessonPrompts.kt` to guide the model's behavior. The prompts will instruct the model to:
        *   Evaluate user responses.
        *   If correct, provide positive reinforcement and ask a follow-up question to deepen understanding.
        *   If incorrect, gently correct the user, provide a hint, or re-explain the concept, then ask a related question.
    *   **Files to Modify:** `app/src/main/java/com/example/acugemma/ai/LessonPrompts.kt`

*   **Task 3.3: Address Model Not Responding (Investigation & Fix) (2-3 hours)**
    *   **Description:** Investigate the root cause of the model sometimes not responding. This may involve debugging the `generateResponseAsync` callback, checking for potential deadlocks, or ensuring proper thread management.
    *   **Files to Modify:** `app/src/main/java/com.example/acugemma/ai/GemmaAiService.kt`, `app/src/main/java/com.example/acugemma/ui/viewmodels/LessonViewModel.kt`

*   **Task 3.4: Implement Graceful Interruption Handling (1-2 hours)**
    *   **Description:** Ensure the app does not crash when a model's response is interrupted. This will likely involve properly managing the `LlmInferenceSession` lifecycle and cancelling ongoing generation tasks when the screen is exited or the app is backgrounded.
    *   **Files to Modify:** `app/src/main/java/com.example/acugemma/ai/GemmaAiService.kt`, `app/src/main/java/com.example/acugemma/ui/viewmodels/LessonViewModel.kt`

### Phase 4: Progress Tracking (Estimated: 4-6 hours)

*   **Task 4.1: Design and Implement Progress Data Model (1-2 hours)**
    *   **Description:** Define data classes to store user progress, including lessons attempted, questions answered, and correctness.
    *   **Files to Modify:** `app/src/main/java/com.example/acugemma/data/` (new file, e.g., `UserProgress.kt`).

*   **Task 4.2: Implement Progress Storage (e.g., SharedPreferences) (1-2 hours)**
    *   **Description:** Integrate a mechanism (like Android's SharedPreferences or a simple local file) to persist user progress data across app sessions.
    *   **Files to Modify:** `app/src/main/java/com.example/acugemma/data/` (new file, e.g., `ProgressRepository.kt`), `LessonViewModel.kt`.

*   **Task 4.3: Develop Progress Screen UI (2-3 hours)**
    *   **Description:** Create a new Composable screen to display the user's progress, showing lessons attempted and the number of correctly answered questions.
    *   **New Files:** `app/src/main/java/com.example/acugemma/ui/screens/ProgressScreen.kt`.
    *   **Files to Modify:** `MainActivity.kt` (to add navigation to `ProgressScreen`).

### Phase 5: Future Enhancements (Beyond 20 hours - for consideration)

*   **Task 5.1: Quiz Mode Implementation**
    *   **Description:** Develop a dedicated quiz mode for each lesson, where the model asks specific questions and evaluates user answers. This would involve new prompt structures, answer parsing, and scoring logic.
*   **Task 5.2: Advanced Error Handling & User Feedback**
    *   **Description:** Implement more granular error handling for AI responses and provide clearer user feedback (e.g., "AcuGemma is thinking...", "I didn't understand that, can you rephrase?").
*   **Task 5.3: Performance Monitoring & Optimization**
    *   **Description:** Continuously monitor app performance, especially model inference time, and explore further optimizations (e.g., using smaller Gemma variants if available, optimizing prompt length).
*   **Task 5.4: Accessibility Improvements**
    *   **Description:** Ensure the app is accessible to users with disabilities (e.g., proper content descriptions, sufficient contrast).
*   **Task 5.5: Model Updates & Versioning Strategy**
    *   **Description:** Plan for how to update the local Gemma model in future app versions.
*   **Task 5.6: Personalization Features**
    *   **Description:** Explore ways to personalize lessons based on a user's learning style, past performance, or stated preferences.

This plan prioritizes the immediate concerns and core functionality within the given timeframe, while also outlining potential future improvements.
