package com.example.acugemma.data.math

import com.example.acugemma.data.LessonStep

object TimeLessonContent {
    val timeLesson = listOf(
        LessonStep(
            content = "Hi! Let's learn about telling time. A clock has two hands. The short, thick hand is the hour hand. It tells us the hour. The long, thin hand is the minute hand. It tells us the minutes. ⏰",
            question = "What does the short hand on a clock tell us?",
            expectedAnswer = "The hour"
        ),
        LessonStep(
            content = "When the minute hand is pointing straight up to the number 12, it's a new hour. We say 'o'clock'. If the hour hand is on the 3 and the minute hand is on the 12, it's 3 o'clock. ",
            question = "What time is it when the short hand is on the 5 and the long hand is on the 12?",
            expectedAnswer = "5 o'clock"
        ),
        LessonStep(
            content = "The numbers on the clock stand for hours, but the numbers for minutes are different. We count by fives around the clock for the minutes: 5, 10, 15, 20... If the minute hand is on the 1, that's 5 minutes. If it's on the 2, that's 10 minutes. ️",
            question = "If the minute hand is on the 4, how many minutes is it?",
            expectedAnswer = "20"
        ),
        LessonStep(
            content = "Let's put it together. If the hour hand is on the 2 and the minute hand is on the 3, it's 2:15. We say 'two fifteen'. ",
            question = "What time is it if the hour hand is on the 7 and the minute hand is on the 6?",
            expectedAnswer = "7:30"
        ),
        LessonStep(
            content = "Great job! Keep practicing telling time. It helps us know when to play, eat, and sleep! ",
            question = "What is the difference between the hour hand and the minute hand?",
            expectedAnswer = "The hour hand is short, and the minute hand is long."
        )
    )
}