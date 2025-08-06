package com.example.acugemma.data.math

import com.example.acugemma.data.LessonStep

object MoneyLessonContent {
    val moneyLesson = listOf(
        LessonStep(
            content = "Hello! Let's learn about money. We use money to buy things we want or need. This is a penny. A penny is worth 1 cent. ",
            question = "How many cents is a penny worth?",
            expectedAnswer = "1"
        ),
        LessonStep(
            content = "This is a nickel. A nickel is worth 5 cents. It's bigger than a penny. ",
            question = "How many pennies are in a nickel?",
            expectedAnswer = "5"
        ),
        LessonStep(
            content = "Next is a dime. A dime is smaller than a nickel but is worth more! A dime is worth 10 cents. ",
            question = "Which coin is worth 10 cents?",
            expectedAnswer = "A dime"
        ),
        LessonStep(
            content = "The biggest coin we'll learn about today is a quarter. A quarter is worth 25 cents. ",
            question = "How many cents is a quarter worth?",
            expectedAnswer = "25"
        ),
        LessonStep(
            content = "You can add coins together to find a total amount of money. If you have 2 pennies, you have 2 cents. If you have 1 dime and 1 penny, you have 11 cents! ",
            question = "If you have 1 nickel and 1 penny, how much money do you have?",
            expectedAnswer = "6 cents"
        ),
        LessonStep(
            content = "You're a money master! Keep practicing counting coins. It's a very helpful skill! ",
            question = "How many nickels make a dime?",
            expectedAnswer = "2"
        )
    )
}