# Guided Lessons Implementation Plan

This plan outlines the steps to implement a guided lesson approach in the AcuGemma application, based on the provided strategy.

## 1. Structure Lesson Data

**Objective:** Define a structured data model for lesson content using `LessonStep` and create lesson content as lists of `LessonStep` objects.

**Action Items:**
*   **Create `LessonStep` Data Class:**
    *   Define `data class LessonStep(val content: String, val question: String, val expectedAnswer: String)`.
    *   **File:** `app/src/main/java/com/example/acugemma/data/LessonStep.kt` (or similar, to keep data models organized).
*   **Define Lesson Content:**
    *   For each existing lesson (e.g., "addition", "subtraction", "countries"), create a `List<LessonStep>`.
    *   These lists will replace the static prompts in `LessonPrompts.kt`.
    *   Consider creating a new object or class, e.g., `LessonContent.kt`, to hold these lesson step lists.
    *   **File:** `app/src/main/java/com/example/acugemma/data/LessonContent.kt`

## 2. Track Student Progress

**Objective:** Integrate a mechanism to track the student's current position within a lesson.

**Action Items:**
*   **Add `currentLessonIndex` to `LessonViewModel`:**
    *   Introduce a `MutableStateFlow<Int>` or `mutableStateOf<Int>` (if using Compose state directly in ViewModel) to hold the `currentLessonIndex`.
    *   Initialize it to `0` when a lesson starts.
    *   **File:** `app/src/main/java/com/example/acugemma/ui/viewmodels/LessonViewModel.kt`
*   **Store Current Lesson Steps:**
    *   The `LessonViewModel` will need access to the `List<LessonStep>` for the currently selected `topicId`.
    *   This can be passed during initialization or retrieved from `LessonContent.kt`.
    *   **File:** `app/src/main/java/com/example/acugemma/ui/viewmodels/LessonViewModel.kt`

## 3. Build Dynamic Prompts

**Objective:** Implement the `buildDynamicPrompt` function to dynamically construct prompts for the AI model.

**Action Items:**
*   **Create `buildDynamicPrompt` Function:**
    *   Implement the `fun buildDynamicPrompt(step: LessonStep, userResponse: String? = null): String` as described in the strategy.
    *   This function will combine the tutor persona, current lesson step content, question, expected answer, and the user's previous response.
    *   **File:** `app/src/main/java/com/example/acugemma/ai/LessonPrompts.kt` (or a new utility file if it becomes too large).

## 4. Integrate into Chat Flow

**Objective:** Modify the `LessonViewModel` and its interaction with `GemmaAiService` to use the new guided lesson logic.

**Action Items:**
*   **Modify `LessonViewModel.init`:**
    *   When `startLesson()` is called, retrieve the first `LessonStep` using `currentLessonIndex`.
    *   Call `buildDynamicPrompt(firstLessonStep)` (without `userResponse`) to get the initial prompt.
    *   Send this initial prompt to `gemmaAiService.generateResponseAsync()`.
    *   **File:** `app/src/main/java/com/example/acugemma/ui/viewmodels/LessonViewModel.kt`
*   **Modify `LessonViewModel.sendMessage`:**
    *   When a user sends a message:
        *   Get the `currentLessonStep` based on `currentLessonIndex`.
        *   Compare the `message` (user's response) with `currentLessonStep.expectedAnswer`.
        *   **If correct:**
            *   Increment `currentLessonIndex`.
            *   Check if there's a next step. If so, retrieve it.
            *   Call `buildDynamicPrompt(nextLessonStep, userResponse)` to generate the AI's response (praise + next question).
            *   If no next step, call `buildDynamicPrompt(lastLessonStep, userResponse)` to generate the completion message.
        *   **If incorrect:**
            *   Do NOT increment `currentLessonIndex`.
            *   Call `buildDynamicPrompt(currentLessonStep, userResponse)` to generate the AI's response (encouragement + hint + re-ask current question).
        *   Send the dynamically generated prompt to `gemmaAiService.generateResponseAsync()`.
    *   Update `_uiState` to reflect the conversation history and processing state.
    *   **File:** `app/src/main/java/com/example/acugemma/ui/viewmodels/LessonViewModel.kt`
*   **Update `LessonPrompts.kt`:**
    *   Remove the existing static prompt constants (e.g., `ADDITION_PROMPT`, `COUNTRIES_PROMPT`) as their content will now be managed by `LessonContent.kt` and `buildDynamicPrompt`.
    *   The `getPrompt` function will no longer be needed in its current form.
    *   **File:** `app/src/main/java/com/example/acugemma/ai/LessonPrompts.kt`

## Dependencies and Considerations:

*   **Error Handling:** Implement robust error handling for cases like `currentLessonIndex` going out of bounds or `LessonContent` not being found for a `topicId`.
*   **UI Updates:** Ensure the UI (Compose Composables) correctly observes and reacts to changes in `LessonViewModel._uiState` to display the AI's responses and manage user input.
*   **Testing:** Develop unit tests for `buildDynamicPrompt` and integration tests for `LessonViewModel` to ensure the guided flow works as expected.
*   **Model Response Length:** The prompt explicitly asks for short and focused responses. Monitor the AI's output to ensure it adheres to this.
*   **User Experience:** Consider how to visually indicate progress through the lesson (e.g., a progress bar or step counter).
*   **Refinement:** The `expectedAnswer` is a simple string comparison. For more complex answers (e.g., numerical ranges, multiple correct phrases), more sophisticated validation logic might be needed.
