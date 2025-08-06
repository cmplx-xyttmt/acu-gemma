package com.example.acugemma.data

import com.example.acugemma.data.math.AdditionLessonContent
import com.example.acugemma.data.math.MultiplicationDivisionLessonContent
import com.example.acugemma.data.math.ShapesLessonContent
import com.example.acugemma.data.math.MoneyLessonContent
import com.example.acugemma.data.math.TimeLessonContent
import com.example.acugemma.data.geography.GeographyLessonContent

object LessonContent {

    fun getLessonSteps(topicId: String): List<LessonStep>? {
        return when (topicId) {
            "addition" -> AdditionLessonContent.additionLesson
            "multiplication" -> MultiplicationDivisionLessonContent.multiplicationLesson
            "shapes" -> ShapesLessonContent.shapesLesson
            "money" -> MoneyLessonContent.moneyLesson
            "time" -> TimeLessonContent.timeLesson
            "countries" -> GeographyLessonContent.countriesLesson
            "continents" -> GeographyLessonContent.continentsLesson
            "oceans" -> GeographyLessonContent.oceansLesson
            "weather" -> GeographyLessonContent.weatherLesson
            "maps" -> GeographyLessonContent.mapsLesson
            "capitals" -> GeographyLessonContent.capitalsLesson
            else -> null
        }
    }
}