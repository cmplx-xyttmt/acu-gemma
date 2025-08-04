# AcuGemma AI Integration Plan (48 Hours)

This plan outlines the steps to integrate a local Gemma model into the AcuGemma app to create an AI-powered learning experience.

### **Phase 1: Foundation & Model Integration (First 8 hours)**

1.  **Set up Dependencies:** Add the necessary dependencies for TensorFlow Lite and the MediaPipe Text Generation Task Library to the `app/build.gradle.kts` file. This will enable on-device inference.
2.  **Integrate Gemma Model:** Add the Gemma 2B model in the `.tflite` format to the project's `assets` directory.
3.  **Create an AI Service:** Develop a dedicated service, let's call it `GemmaAiService`, to encapsulate all logic related to interacting with the Gemma model. This service will handle model loading, prompt generation, and response handling.

### **Phase 2: Building the Lesson Experience (Next 16 hours)**

4.  **Develop the Lesson Screen:** Create a new `LessonScreen` composable. This screen will feature a chat-like interface where the user can interact with the AI tutor.
5.  **Implement a `LessonViewModel`:** Create a `LessonViewModel` to manage the state of the `LessonScreen`. This will include the conversation history, user input, and the AI's responses. It will use the `GemmaAiService` to get the AI's responses.
6.  **Update Navigation:** Update the app's navigation to allow users to go from the `SubjectDetailScreen` to the new `LessonScreen` when they tap on a topic.

### **Phase 3: Content and AI Interaction (Next 16 hours)**

7.  **Develop Lesson Prompts:** Create specific, detailed prompts for the four lessons (two for Mathematics, two for Geography). These prompts will be designed to guide the AI in teaching the concepts effectively.
8.  **Implement AI Interaction Logic:** Implement the core logic for the AI interaction in the `LessonViewModel`. This will involve:
    *   Sending the initial lesson prompt to the `GemmaAiService`.
    *   Sending the user's messages to the service.
    *   Receiving the AI's responses and updating the UI.
9.  **Refine the User Experience:** Refine the `LessonScreen` to provide a smooth and engaging user experience. This will include features like displaying a "typing" indicator while the AI is generating a response.

### **Phase 4: Testing and Finalization (Last 8 hours)**

10. **End-to-End Testing:** Thoroughly test the complete workflow, from selecting a topic to completing a lesson with the AI. Focus on the four initial lessons to ensure they are working as expected.
11. **Add Basic Error Handling:** Implement error handling to gracefully manage potential issues, such as the model failing to load or generate a response.
12. **Final Review:** Conduct a final review of the code and the app to ensure it is ready for the next stage of development.
