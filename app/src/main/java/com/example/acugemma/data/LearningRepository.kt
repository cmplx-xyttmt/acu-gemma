package com.example.acugemma.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Tag
import com.example.acugemma.R

object LearningRepository {

    fun getSubjects(): List<Subject> = listOf(
        Subject(
            id = "math",
            name = "Mathematics",
            description = "Explore numbers, shapes, and problem-solving",
            imageDrawableInt = R.drawable.math_image,
            topics = getMathTopics()
        ),
        Subject(
            id = "geography",
            name = "Geography",
            description = "Discover the world, its landscapes, and cultures.",
            imageDrawableInt = R.drawable.geography_image,
            topics = getGeographyTopics()
        )
    )

    private fun getMathTopics(): List<Topic> = listOf(
        Topic("counting", "Counting", "Numbers and Operations", Icons.Default.Tag),
        Topic("addition", "Addition", "Numbers and Operations", Icons.Default.Add),
        Topic("subtraction", "Subtraction", "Numbers and Operations", Icons.Default.Remove),
        Topic("shapes", "Shapes", "Geometry", Icons.Default.CropSquare),
        Topic("time", "Time", "Measurement", Icons.Default.Schedule)
    )

    private fun getGeographyTopics(): List<Topic> = listOf(
        Topic("countries", "Countries", "World Geography", Icons.Default.Public),
        Topic("continents", "Continents", "World Geography", Icons.Default.Language),
        Topic("capitals", "Capitals", "World Geography", Icons.Default.LocationCity)
    )

    fun getSubjectByName(name: String): Subject? =
        getSubjects().find { it.name.equals(name, ignoreCase = true) }
}
