package com.example.acugemma.data.math

import com.example.acugemma.data.LessonStep

object ShapesLessonContent {
    val shapesLesson = listOf(
        LessonStep(
            content = "Hi! Today we're going to learn about shapes. The first shape is a square. A square has 4 sides that are all the same length, and 4 corners. Can you find a square in your room? ",
            question = "How many sides does a square have?",
            expectedAnswer = "4"
        ),
        LessonStep(
            content = "Next is a circle. A circle is a round shape with no straight sides and no corners. Think of a ball or a yummy cookie! ",
            question = "Does a circle have any corners?",
            expectedAnswer = "no"
        ),
        LessonStep(
            content = "This is a triangle. A triangle has 3 straight sides and 3 corners. Can you make a triangle with your hands? ",
            question = "How many sides does a triangle have?",
            expectedAnswer = "3"
        ),
        LessonStep(
            content = "Look at this rectangle. A rectangle also has 4 sides and 4 corners, but not all of its sides are the same length. Two sides are long and two sides are short. ",
            question = "What is the difference between a square and a rectangle?",
            expectedAnswer = "A square has all equal sides, but a rectangle does not."
        ),
        LessonStep(
            content = "Let's learn about an oval. An oval is like a stretched-out circle. It is round, but not perfectly round like a circle. Think of an egg! ",
            question = "Is an oval a circle?",
            expectedAnswer = "no"
        ),
        LessonStep(
            content = "Great job! Now you know about some basic shapes. Look around and see how many you can find! ",
            question = "What shape has 3 sides and 3 corners?",
            expectedAnswer = "A triangle"
        )
    )
}