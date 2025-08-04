package com.example.acugemma.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object SubjectDetail: Screen("subject_detail/{subjectName}") {
        fun createRoute(subjectName: String) = "subject_detail/$subjectName"
    }
    object Lesson: Screen("lesson/{topicId}") {
        fun createRoute(topicId: String) = "lesson/$topicId"
    }
}